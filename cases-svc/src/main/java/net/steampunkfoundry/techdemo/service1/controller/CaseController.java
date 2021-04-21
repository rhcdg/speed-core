package net.steampunkfoundry.techdemo.service1.controller;

import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Case.Status;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence.DocType;
import net.steampunkfoundry.techdemo.service1.dto.Adjudicator;
import net.steampunkfoundry.techdemo.service1.dto.CaseDto;
import net.steampunkfoundry.techdemo.service1.dto.CaseHistory;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.dto.Pdf;
import net.steampunkfoundry.techdemo.service1.exceptions.InvalidParameterException;
import net.steampunkfoundry.techdemo.service1.exceptions.MissingParameterException;
import net.steampunkfoundry.techdemo.service1.pdf.ApprovalNoticeData;
import net.steampunkfoundry.techdemo.service1.pdf.ApprovalNoticePdfGenerator;
import net.steampunkfoundry.techdemo.service1.pdf.ReceiptData;
import net.steampunkfoundry.techdemo.service1.pdf.ReceiptPdfGenerator;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import net.steampunkfoundry.techdemo.service1.service.AdjudicatorClient;
import net.steampunkfoundry.techdemo.service1.service.CaseHistoryService;
import net.steampunkfoundry.techdemo.service1.service.CaseService;
import net.steampunkfoundry.techdemo.service1.service.FormsClient;
import org.owasp.encoder.Encode;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CaseController {

    private static final String ADJUDICATOR_ID = "Adjudicator Id";
    private static final String CASE_NUMBER = "Case Number";
    private final CaseService service;
    private final CaseHistoryService caseHistoryService;
    private final CaseRepository repo;
    private final AdjudicatorClient adjudicatorClient;
    private final FormsClient formsClient;
    private final ReceiptPdfGenerator feeAcceptancePdfGenerator;
    private final ApprovalNoticePdfGenerator approvalPdfGenerator;

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find All Cases", notes = "This method returns a list of all cases.")
    @GetMapping(value = "/cases/findAll")
    public @ResponseBody List<CaseDto> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);

        List<Case> cases = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());

        return cases.stream().map(o -> convertToDto(o, adjudicatorMap, formsMap)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Id", notes = "This method returns a case for a given id.")
    @GetMapping(value = "/cases/{id}")
    public @ResponseBody CaseDto findById(
            @ApiParam(
                    name = "id",
                    type = "Long",
                    value = "Case Id")
            @PathVariable Long id) {
        Optional<Case> result = repo.findById(id);
        if (result.isPresent()) {
            Case foundCase = result.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
            Form form = formsClient.getFormByFormNumber(auth, foundCase.getFormNumber());

            return convertToDto(result.get(), adjudicatorMap, form);
        }
        throw new ResourceNotFoundException();
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Status", notes = "This method returns a list of all cases for a given status.")
    @GetMapping(value = "/cases/findByStatus")
    public @ResponseBody List<CaseDto> findByStatus(
            @ApiParam(
                    name = "status",
                    type = "String",
                    value = "Case current status")
            @RequestParam String status) throws InvalidParameterException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        List<Case> cases = repo.findByStatus(validateAndConvertStatus(status));

        return cases.stream().map(o -> convertToDto(o, adjudicatorMap, formsMap)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Adjudicator", notes = "This method returns a list of all cases for a given adjudicator.")
    @GetMapping(value = "/cases/findByAdjudicatorId")
    public @ResponseBody List<CaseDto> findByAdjudicatorId(
            @ApiParam(
                    name = "adjudicatorId",
                    type = "String",
                    value = "Id of the adjudicator assigned to the case")
            @RequestParam String adjudicatorId)
            throws MissingParameterException {
        verifyOrThrowIfEmpty(ADJUDICATOR_ID, adjudicatorId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        List<Case> cases = repo.findByAdjudicatorId(Encode.forHtml(adjudicatorId));

        return cases.stream().map(o -> convertToDto(o, adjudicatorMap, formsMap)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Adjudicator and Status",
            notes = "This method returns a list of all cases for a given adjudicator and status.")
    @GetMapping(value = "/cases/findByAdjudicatorIdAndStatus")
    public @ResponseBody List<CaseDto> findByAdjudicatorIdAndStatus(
            @ApiParam(
                    name = "adjudicatorId",
                    type = "String",
                    value = "Id of the adjudicator assigned to the case")
            @RequestParam String adjudicatorId,
            @ApiParam(
                    name = "status",
                    type = "String",
                    value = "Case current status")
            @RequestParam String status)
            throws MissingParameterException, InvalidParameterException {
        verifyOrThrowIfEmpty(ADJUDICATOR_ID, adjudicatorId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        List<Case> cases = repo.findByAdjudicatorIdAndStatus(Encode.forHtml(adjudicatorId),
                validateAndConvertStatus(status));

        return cases.stream().map(o -> convertToDto(o, adjudicatorMap, formsMap)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Case Number",
            notes = "This method returns a list of all cases for a given case number.")
    @GetMapping(value = "/cases/findByCaseNumber")
    public @ResponseBody List<CaseDto> findByCaseNumber(
            @ApiParam(
                    name = "caseNumber",
                    type = "String",
                    value = "Case number assigned at case creation")
            @RequestParam String caseNumber)
            throws MissingParameterException {
        verifyOrThrowIfEmpty(CASE_NUMBER, caseNumber);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        List<Case> cases = repo.findByCaseNumber(Encode.forHtml(caseNumber));

        return cases.stream().map(o -> convertToDto(o, adjudicatorMap, formsMap)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('Applicant', 'Adjudicator')")
    @ApiOperation(value = "Create a Case",
            notes = "This method creates a case a new case object.")
    @PostMapping(value = "/api/case")
    public @ResponseBody Case createCase(@ApiParam(
            name = "case",
            type = "Case object",
            value = "Required fields are anumber, applicantId, and formNumber")
            @RequestBody CaseDto caseDto) throws MissingParameterException {
        verifyOrThrowIfEmpty("A-Number", caseDto.getANumber());
        verifyOrThrowIfEmpty("Applicant Id", caseDto.getApplicantId());
        verifyOrThrowIfEmpty("Form Number", caseDto.getFormNumber());

        Case newCase = new Case();
        BeanUtils.copyProperties(caseDto, newCase);
        return service.createCorrespondence(service.create(newCase), DocType.FEE_RECEIPT);
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Assign Adjudicator",
            notes = "This method assigns an adjudicator and triggers security checks for a given case.")
    @PutMapping(value = "/cases/assignAdjudicator")
    public @ResponseBody CaseDto assignAdjudicator(
            @ApiParam(
                    name = "case",
                    type = "Case object",
                    value = "Accepts the full case model")
            @RequestBody CaseDto caseDto) throws MissingParameterException {
        verifyOrThrowIfEmpty(ADJUDICATOR_ID, caseDto.getAdjudicatorId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        Case caseObject = new Case();
        BeanUtils.copyProperties(caseDto, caseObject);
        return convertToDto(service.requestSecurityCheck(auth, caseObject), adjudicatorMap, formsMap);
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Initiate Security Check",
            notes = "This method triggers security checks for a given case.")
    @PutMapping(value = "/cases/initiateSecurityCheck")
    public @ResponseBody CaseDto initiateSecurityCheck(
            @ApiParam(
                    name = "case",
                    type = "Case object",
                    value = "Accepts the full case model")
            @RequestBody CaseDto caseDto) throws MissingParameterException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Map<String, Form> formsMap = formsClient.getAllForms(auth);

        Case caseObject = new Case();
        BeanUtils.copyProperties(caseDto, caseObject);
        return convertToDto(service.requestSecurityCheck(auth, caseObject), adjudicatorMap, formsMap);
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Approve Case",
            notes = "This method approves a case and generates the appropriate correspondence.")
    @PutMapping(value = "/cases/approve")
    public @ResponseBody CaseDto approve(
            @ApiParam(
                    name = "case",
                    type = "Case object",
                    value = "Accepts the full case model")
            @RequestBody CaseDto caseDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        Form form = formsClient.getFormByFormNumber(auth, caseDto.getFormNumber());

        Case caseObject = new Case();
        BeanUtils.copyProperties(caseDto, caseObject);
        return convertToDto(service.createCorrespondence(caseObject, DocType.APPROVAL_RECEIPT), adjudicatorMap, form);
    }

    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Get Case History",
            notes = "This method returns the case history for a given case.")
    @GetMapping(value = "/cases/history")
    public @ResponseBody Set<CaseHistory> getCaseHistory(
            @ApiParam(
                    name = "caseId",
                    type = "Long",
                    value = "Case Id")
            @RequestParam Long caseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Adjudicator> adjudicatorMap = adjudicatorClient.getAdjudicators(auth);
        return repo.findById(caseId).map(kase -> caseHistoryService.getCaseHistory(kase).stream().map(caseHistory -> {
            caseHistory.setAdjudicatorName(adjudicatorMap.get(caseHistory.getAdjudicator()).getName());
            return caseHistory;
        }).collect(Collectors.toSet())).orElseGet(HashSet::new);
    }

    @GetMapping(value = "/cases/getPrintablePdfs")
    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @ApiOperation(value = "Get Printable PDFs",
            notes = "This method returns a list of printable PDFs for all case correspondence.")
    public @ResponseBody Set<Pdf> getPrintablePdfs() throws IOException {

        List<Case> cases = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        Set<Pdf> pdfs = new HashSet<Pdf>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Form> formsMap = formsClient.getAllForms(auth);
        Calendar now = Calendar.getInstance();
        try {
            for (Case kase : cases) {
                for (Correspondence corr : kase.getCorrespondences()) {
                    String caseNumber = kase.getCaseNumber();
                    String pdfName = null;
                    InputStream inputStream = null;
                    Form form = formsMap.get(kase.getFormNumber());
                    String address = createAddress(form);
                    if (Correspondence.DocType.FEE_RECEIPT.toString().equals(corr.getDocType())) {
                        ReceiptData receiptData = new ReceiptData();
                        receiptData.setReceiptNumber(
                                "VSC " + getFiscalYear(now) + " " + kase.getCreatedDate().getDayOfYear() + " "
                                        + caseNumber.substring(caseNumber.length() - 5));
                        receiptData.setAddress(address);
                        receiptData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
                        receiptData.setNoticeDate(corr.getLastModified().toString());
                        receiptData.setNoticeType("Receipt Notice\n"
                                + "Amount received: $1000.50 U.S. Section: Alien of Extraordinary Ability, Sec.203(b)");
                        receiptData.setPriorityDate(corr.getLastModified().toString());
                        receiptData.setReceivedDate(corr.getLastModified().toString());
                        // Gov did not provide instructures on value here
                        receiptData.setUscisOnlineAccountNumber("UNKNOWN");
                        inputStream = feeAcceptancePdfGenerator.generateReceiptPdf(receiptData);
                        pdfName = Correspondence.DocType.FEE_RECEIPT.toString();
                    } else if (Correspondence.DocType.APPROVAL_RECEIPT.toString().equals(corr.getDocType())) {
                        ApprovalNoticeData receiptData = new ApprovalNoticeData();
                        receiptData.setReceiptNumber(
                                "VSC " + getFiscalYear(now) + " " + kase.getCreatedDate().getDayOfYear() + " "
                                        + caseNumber.substring(caseNumber.length() - 5));
                        receiptData.setAddress(address);
                        receiptData.setApplicant(kase.getANumber());
                        receiptData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
                        receiptData.setNoticeDate(corr.getLastModified().toString());
                        receiptData.setNoticeType("Receipt Notice\n"
                                + "Amount received: $1000.50 U.S. Section: Alien of Extraordinary Ability, Sec.203(b)");
                        receiptData.setPriorityDate(corr.getLastModified().toString());
                        receiptData.setReceivedDate(corr.getLastModified().toString());
                        // Gov did not provide instructions on value here
                        receiptData.setUscisOnlineAccountNumber("UNKNOWN");
                        inputStream = approvalPdfGenerator.generateApprovalNoticePdf(receiptData);
                        pdfName = Correspondence.DocType.APPROVAL_RECEIPT.toString();
                    } else {
                        inputStream = new ClassPathResource("/fakepdf/fake.pdf").getInputStream();
                        pdfName = "PDF Receipt";
                    }
                    pdfs.add(Pdf.builder().aNumber(kase.getANumber()).caseNumber(caseNumber)
                            .pdfContent(inputStream.readAllBytes()).pdfDescription(pdfName).build());
                }
            }

            return pdfs;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private String createAddress(Form form) {
        String address = null;
        if (form != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(form.getAddress()).append(System.lineSeparator()).append(form.getAddress2())
                    .append(System.lineSeparator()).append(form.getCity()).append(Character.SPACE_SEPARATOR)
                    .append(form.getState()).append(Character.SPACE_SEPARATOR).append(form.getZipCode());
            address = sb.toString();
        } else {
            address = "UNKNOWN";
        }
        return address;
    }

    private int getFiscalYear(Calendar calendarDate) {
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        // return the last 2 digits of the fiscal year
        return ((month >= Calendar.MARCH) ? year : year - 1) % 100;
    }

    private void verifyOrThrowIfEmpty(@RequestParam String name, String value) throws MissingParameterException {
        if (StringUtils.isBlank(value)) {
            throw new MissingParameterException(name);
        }
    }

    private Status validateAndConvertStatus(String status) throws InvalidParameterException {
        try {
            return Status.valueOf(Encode.forHtml(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("status");
        }
    }

    private CaseDto convertToDto(Case caseObject, Map<String, Adjudicator> adjudicatorMap, Map<String, Form> formMap) {
        if (caseObject != null) {
            CaseDto caseDto = new CaseDto();
            BeanUtils.copyProperties(caseObject, caseDto);

            // Set Adjudicator Name field
            Optional<String> adjudicatorId = Optional.ofNullable(caseObject.getAdjudicatorId());
            adjudicatorId.ifPresent(s -> {
                Optional<Adjudicator> adjudicator = Optional.ofNullable(adjudicatorMap.get(s));
                adjudicator.ifPresent(a -> {
                    caseDto.setAdjudicatorName(a.getName());
                });
            });

            //Merge in Form
            Optional<String> formNumber = Optional.ofNullable(caseObject.getFormNumber());
            formNumber.ifPresent(n -> {
                Optional<Form> form = Optional.ofNullable(formMap.get(n));
                form.ifPresent(f -> {
                    caseDto.setForm(f);
                });
            });

            return caseDto;
        }

        return null;
    }

    private CaseDto convertToDto(Case caseObject, Map<String, Adjudicator> adjudicatorMap,
            Form form) {
        CaseDto caseDto = new CaseDto();
        BeanUtils.copyProperties(caseObject, caseDto);

        //Set Adjudicator Name field
        Optional<String> adjudicatorId = Optional.ofNullable(caseObject.getAdjudicatorId());
        adjudicatorId.ifPresent(s -> {
            Optional<Adjudicator> adjudicator = Optional.ofNullable(adjudicatorMap.get(s));
            adjudicator.ifPresent(a -> {
                caseDto.setAdjudicatorName(a.getName());
            });
        });

        //Merge in Form
        caseDto.setForm(form);

        return caseDto;
    }
}
