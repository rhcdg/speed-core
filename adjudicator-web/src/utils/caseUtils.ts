export interface CaseDetailPanelProps {
  kase: Case;
  updateCase?: (c: Case, showSuccess?: boolean) => void;
  addressType?: 'home' | 'delivery';
}

export enum CaseStatus {
  Accepting = 'ACCEPTING',
  Submitted = 'SUBMITTED',
  Validated = 'VALIDATED',
  Rejected = 'REJECTED'
}

interface Form {
  anumber: string | null;
  firstName: string | null;
  lastName: string | null;
  middleName: string | null;
  applicationType: string | null;
  inCareOf: string | null;
  countryOfBirth: string | null;
  countryOfCitizenship: string | null;
  visaClass: string | null;
  dateOfBirth: string | null;
  gender: string | null;
  address: string | null;
  address2: string | null;
  city: string | null;
  state: string | null;
  zipCode: string | null;
  postalCode: string | null;
  province: string | null;
  country: string | null;
  numberOfTrips: string | null;
  explanationOfEligibility?: string | null;
  signature: string | null;
  signatureDate: string | null;
  phoneNumber: string | null;
  purposeOfTrip?: string | null;
  countriesVisiting: string | null;
  dateOfDeparture: string | null;
  lengthOfTrip: string | null;
  inProceedings: false;
  previouslyIssued: false;
  deliveryAddress: string | null;
  deliveryAddress2: string | null;
  deliveryCity: string | null;
  deliveryState: string | null;
  deliveryZipCode: string | null;
  deliveryPostalCode: string | null;
  deliveryProvince: string | null;
  deliveryCountry: string | null;
}

interface Correspondence {
  id: number;
  docType: string;
  documentKey: string;
  createdBy: string;
  createdDate: string;
  lastModified: string | null;
  lastModifiedBy: string | null;
  caseId: number;
}

interface SecurityCheck {
  id: number;
  securityCheckType: string;
  securityCheckStatus: string;
  createdBy: string;
  createdAt: string;
  lastModified: string | null;
  lastModifiedBy: string | null;
  caseId: number;
}

export interface Case {
  id: number;
  caseNumber: string;
  formId: number;
  form: Form;
  adjudicatorId: string | null;
  status: CaseStatus;
  anumber: string;
  createdBy: string;
  createdDate: string;
  lastModified: string | null;
  lastModifiedBy: string | null;
  supportingEvidenceValidated: boolean;
  caseCorrespondenceValidated: boolean;
  securityCheckValidated: boolean;
  decisionJustification: string | null;
  correspondences: Array<Correspondence | null>;
  securityChecks: Array<SecurityCheck | null>;
}

export interface CaseHistory {
  caseNumber: string;
  event: string;
  dateStarted: string;
  time: string;
  adjudicator: string;
  revNumber: number;
  modDate: string;
  adjudicatorName: string;
}
