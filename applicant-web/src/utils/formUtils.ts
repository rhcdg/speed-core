import * as Yup from 'yup';

const charLimitMessage = (fieldName: string) =>
  `${fieldName} must not exceed 256 characters`;
const isRequiredMessage = (fieldName: string) => `${fieldName} is required`;
const nameRegexMatchMessage =
  'Name must use only letters, hyphens, apostrophes, or spaces';
const dateFormatMsg = 'Date must be in MM/DD/YYYY format';

export const I131ValidationSchema = Yup.object({
  anumber: Yup.string()
    .matches(
      /^\d{7,9}$/,
      'ANumber must be a number between 7 and 9 digits long'
    )
    .required(isRequiredMessage('A-Number')),
  dateOfBirth: Yup.string()
    .matches(
      /(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d/i,
      dateFormatMsg
    )
    .required(isRequiredMessage('Date of Birth')),
  gender: Yup.string().required(isRequiredMessage('Gender')),
  lastName: Yup.string()
    .trim()
    .matches(/^[A-Za-z'\- ]+$/, nameRegexMatchMessage)
    .max(256, charLimitMessage('Last Name'))
    .required(isRequiredMessage('Last Name')),
  firstName: Yup.string()
    .trim()
    .matches(/^[A-Za-z'\- ]+$/, nameRegexMatchMessage)
    .max(256, charLimitMessage('First Name'))
    .required(isRequiredMessage('First Name')),
  middleName: Yup.string()
    .trim()
    .matches(/^[A-Za-z'\- ]+$/, nameRegexMatchMessage)
    .max(256, charLimitMessage('Middle Name')),
  careOfName: Yup.string()
    .trim()
    .matches(/^[A-Za-z'\- ]+$/, nameRegexMatchMessage)
    .max(256, charLimitMessage('In Care Of Name')),
  address: Yup.string()
    .trim()
    .max(256, charLimitMessage('Address'))
    .required(isRequiredMessage('Address')),
  address2: Yup.string().trim().max(256, charLimitMessage('Address2')),
  city: Yup.string()
    .trim()
    .max(256, charLimitMessage('City'))
    .required(isRequiredMessage('City')),
  state: Yup.string().required(isRequiredMessage('State')),
  zipCode: Yup.string()
    .matches(
      /(^\d{5}$)|(^\d{9}$)|(^\d{5}-\d{4}$)/,
      'Must contain 5 or 9 digits, with optional hyphen'
    )
    .required(isRequiredMessage('Zip Code')),
  signature: Yup.string()
    .matches(/^[A-Za-z'\- ]+$/, nameRegexMatchMessage)
    .required(isRequiredMessage('Signature')),
  visaClass: Yup.string().required(isRequiredMessage('Visa Class')),
  ssn: Yup.string().matches(
    /^\d{3} \d{2} \d{4}$/,
    'SSN must be numbers separated by spaces XXX XX XXXX'
  ),
  explanationOfEligibility: Yup.string().max(
    1000,
    'Text cannot exceed 1000 characters'
  ),
  countriesVisiting: Yup.string().max(
    1000,
    'Text cannot exceed 1000 characters'
  ),
  purposeOfTrip: Yup.string().max(1000, 'Text cannot exceed 1000 characters'),
  dateOfDeparture: Yup.string().matches(
    /(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d/i,
    dateFormatMsg
  ),
  dateFileOpen: Yup.string().matches(
    /(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d/i,
    dateFormatMsg
  ),
  signatureDate: Yup.string().matches(
    /(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d/i,
    dateFormatMsg
  ),
  phoneNumber: Yup.string().matches(
    /^[2-9]\d{2}-\d{3}-\d{4}$/,
    'Phone number cannot start with 1, and must be hyphen-separated'
  )
});

export interface SharedProps {
  readOnly?: boolean;
}

export interface SupportDocument {
  dateSubmitted?: string;
  formType: string;
  id: string;
  name: string;
  status: string;
  url: string;
}

export interface I131FormValues {
  address: string;
  address2: string;
  akaFirstName: string;
  akaLastName: string;
  anumber: string;
  applicantId: string;
  applicationType: string;
  city: string;
  countriesVisiting: string;
  country: string;
  countryOfBirth: string;
  countryOfCitizenship: string;
  dateFileOpen: string;
  dateOfBirth: string;
  dateOfDeparture: string;
  dateofEntry: string;
  deliveryAddress: string;
  deliveryAddress2: string;
  deliveryCity: string;
  deliveryCountry: string;
  deliveryPostalCode: string;
  deliveryProvince: string;
  deliveryState: string;
  deliveryZipCode: string;
  driversLicenseNumber: string;
  explanationOfEligibility: string;
  father: string;
  fbiNumber: string;
  fieldControlOffice: string;
  fingerPrintCode: string;
  firstName: string;
  formNumber: string;
  gender: 'FEMALE' | 'MALE';
  i94AdministeredNumbered: string;
  id: string;
  identificationFin: string;
  inCareOf: string;
  inProceedings: true;
  lastName: string;
  lengthOfTrip: string;
  middleName: string;
  mother: string;
  numberOfTrips: string;
  ownSubmission?: boolean; // add value for form display, remove before put/post
  passportNumber: string;
  phoneNumber: string;
  portofEntry: string;
  postalCode: string;
  previouslyIssued: true;
  province: string;
  purposeOfTrip: string;
  signature: {
    name: string;
    date: string;
  };
  signatureDate: string;
  ssn: string;
  state: string;
  submissionDate: string;
  submissionStatus: 'IN_PROGRESS' | 'SUBMITTED';
  supportDocument: SupportDocument[];
  visaClass: string;
  zipCode: string;
}

export const TravelCategoryCardContents = [
  {
    key: 'reentry',
    title: 'Reentry Permit',
    text:
      'A Reentry Permit allows a lawful permanent resident or conditional permanent resident to apply for admission to the United States upon returning from abroad during the permit’s validity without the need to obtain a returning resident visa from a U.S. Embassy or U.S. Consulate.',
    disabled: true
  },
  {
    key: 'refugee',
    title: 'Refugee Travel Document',
    text:
      'A Refugee Travel Document is issued to an individual in valid refugee or asylee status, or to a lawful permanent resident who obtained such status as a refugee or asylee in the United States. Individuals who hold asylee or refugee status and are not lawful permanent residents must have a Refugee Travel Document to return to the United States after travel abroad, unless they possess an Advance Parole Document. A Department of Homeland Security (DHS) officer at the U.S. port-of-entry will determine your admissibility when you present your travel document.',
    disabled: true
  },
  {
    key: 'advanced-parole-internal',
    title: 'Advance Parole Document | Applying from Inside the United States',
    text:
      'Parole allows an alien to physically enter into the United States for a specific purpose. An individual who has been "paroled" has not been admitted to the United States and remains an "applicant for admission" even while paroled. DHS, as a matter of discretion, may issue an Advance Parole Document to authorize an alien to appear at a port-of-entry to seek parole into the United States. The document may be accepted by a transportation company in lieu of a visa as an authorization for the holder to travel to the United States. An Advance Parole Document is not issued to serve in place of any required passport.',
    disabled: false
  },
  {
    key: 'advanced-parole-external',
    title: 'Advance Parole Document | Applying from Outside the United States',
    text:
      'The granting of an Advance Parole Document for individuals outside the United States is an extraordinary measure used sparingly to allow an otherwise inadmissible alien to travel to the United States and to seek parole into the United States for a temporary period of time due to urgent humanitarian reasons or for significant public benefit (significant public benefit parole is typically limited to law enforcement or homeland security-related reasons). An Advance Parole Document cannot be used to circumvent normal visa-issuance procedures and is not a means to bypass delays in visa issuance.',
    disabled: true
  }
];
export const IN_PROGRESS = 'IN_PROGRESS';
export const SUBMITTED = 'SUBMITTED';

export const formTestData: I131FormValues = {
  address: 'string',
  address2: 'string',
  akaFirstName: 'string',
  akaLastName: 'string',
  anumber: 'string',
  applicantId: 'string',
  applicationType: 'string',
  city: 'string',
  countriesVisiting: 'string',
  country: 'string',
  countryOfBirth: 'string',
  countryOfCitizenship: 'string',
  dateFileOpen: 'string',
  dateOfBirth: 'string',
  dateOfDeparture: 'string',
  dateofEntry: 'string',
  deliveryAddress: 'string',
  deliveryAddress2: 'string',
  deliveryCity: 'string',
  deliveryCountry: 'string',
  deliveryPostalCode: 'string',
  deliveryProvince: 'string',
  deliveryState: 'string',
  deliveryZipCode: 'string',
  driversLicenseNumber: 'string',
  explanationOfEligibility: 'string',
  father: 'string',
  fbiNumber: 'string',
  fieldControlOffice: 'string',
  fingerPrintCode: 'string',
  firstName: 'string',
  formNumber: 'string',
  gender: 'FEMALE',
  i94AdministeredNumbered: 'string',
  id: 'string',
  identificationFin: 'string',
  inCareOf: 'string',
  inProceedings: true,
  lastName: 'string',
  lengthOfTrip: 'string',
  middleName: 'string',
  mother: 'string',
  numberOfTrips: 'string',
  passportNumber: 'string',
  phoneNumber: 'string',
  portofEntry: 'string',
  postalCode: 'string',
  previouslyIssued: true,
  province: 'string',
  purposeOfTrip: 'string',
  signature: {
    name: 'string',
    date: 'dateString'
  },
  signatureDate: 'string',
  ssn: 'string',
  state: 'string',
  submissionDate: 'string',
  submissionStatus: 'IN_PROGRESS',
  supportDocument: [],
  visaClass: 'string',
  zipCode: 'string'
};

export interface State {
  name: string;
  abbreviation: string;
}

export const states: State[] = [
  {
    name: 'Alabama',
    abbreviation: 'AL'
  },
  {
    name: 'Alaska',
    abbreviation: 'AK'
  },
  {
    name: 'American Samoa',
    abbreviation: 'AS'
  },
  {
    name: 'Arizona',
    abbreviation: 'AZ'
  },
  {
    name: 'Arkansas',
    abbreviation: 'AR'
  },
  {
    name: 'California',
    abbreviation: 'CA'
  },
  {
    name: 'Colorado',
    abbreviation: 'CO'
  },
  {
    name: 'Connecticut',
    abbreviation: 'CT'
  },
  {
    name: 'Delaware',
    abbreviation: 'DE'
  },
  {
    name: 'District Of Columbia',
    abbreviation: 'DC'
  },
  {
    name: 'Federated States Of Micronesia',
    abbreviation: 'FM'
  },
  {
    name: 'Florida',
    abbreviation: 'FL'
  },
  {
    name: 'Georgia',
    abbreviation: 'GA'
  },
  {
    name: 'Guam',
    abbreviation: 'GU'
  },
  {
    name: 'Hawaii',
    abbreviation: 'HI'
  },
  {
    name: 'Idaho',
    abbreviation: 'ID'
  },
  {
    name: 'Illinois',
    abbreviation: 'IL'
  },
  {
    name: 'Indiana',
    abbreviation: 'IN'
  },
  {
    name: 'Iowa',
    abbreviation: 'IA'
  },
  {
    name: 'Kansas',
    abbreviation: 'KS'
  },
  {
    name: 'Kentucky',
    abbreviation: 'KY'
  },
  {
    name: 'Louisiana',
    abbreviation: 'LA'
  },
  {
    name: 'Maine',
    abbreviation: 'ME'
  },
  {
    name: 'Marshall Islands',
    abbreviation: 'MH'
  },
  {
    name: 'Maryland',
    abbreviation: 'MD'
  },
  {
    name: 'Massachusetts',
    abbreviation: 'MA'
  },
  {
    name: 'Michigan',
    abbreviation: 'MI'
  },
  {
    name: 'Minnesota',
    abbreviation: 'MN'
  },
  {
    name: 'Mississippi',
    abbreviation: 'MS'
  },
  {
    name: 'Missouri',
    abbreviation: 'MO'
  },
  {
    name: 'Montana',
    abbreviation: 'MT'
  },
  {
    name: 'Nebraska',
    abbreviation: 'NE'
  },
  {
    name: 'Nevada',
    abbreviation: 'NV'
  },
  {
    name: 'New Hampshire',
    abbreviation: 'NH'
  },
  {
    name: 'New Jersey',
    abbreviation: 'NJ'
  },
  {
    name: 'New Mexico',
    abbreviation: 'NM'
  },
  {
    name: 'New York',
    abbreviation: 'NY'
  },
  {
    name: 'North Carolina',
    abbreviation: 'NC'
  },
  {
    name: 'North Dakota',
    abbreviation: 'ND'
  },
  {
    name: 'Northern Mariana Islands',
    abbreviation: 'MP'
  },
  {
    name: 'Ohio',
    abbreviation: 'OH'
  },
  {
    name: 'Oklahoma',
    abbreviation: 'OK'
  },
  {
    name: 'Oregon',
    abbreviation: 'OR'
  },
  {
    name: 'Palau',
    abbreviation: 'PW'
  },
  {
    name: 'Pennsylvania',
    abbreviation: 'PA'
  },
  {
    name: 'Puerto Rico',
    abbreviation: 'PR'
  },
  {
    name: 'Rhode Island',
    abbreviation: 'RI'
  },
  {
    name: 'South Carolina',
    abbreviation: 'SC'
  },
  {
    name: 'South Dakota',
    abbreviation: 'SD'
  },
  {
    name: 'Tennessee',
    abbreviation: 'TN'
  },
  {
    name: 'Texas',
    abbreviation: 'TX'
  },
  {
    name: 'Utah',
    abbreviation: 'UT'
  },
  {
    name: 'Vermont',
    abbreviation: 'VT'
  },
  {
    name: 'Virgin Islands',
    abbreviation: 'VI'
  },
  {
    name: 'Virginia',
    abbreviation: 'VA'
  },
  {
    name: 'Washington',
    abbreviation: 'WA'
  },
  {
    name: 'West Virginia',
    abbreviation: 'WV'
  },
  {
    name: 'Wisconsin',
    abbreviation: 'WI'
  },
  {
    name: 'Wyoming',
    abbreviation: 'WY'
  }
];

export interface Country {
  name: string;
  code: string;
}

export const countries = [
  { name: 'Argentina', code: 'AR' },
  { name: 'PGASU', code: 'PG' },
  { name: 'RECUX', code: 'RC' },
  { name: 'AustraZAJJIlia', code: 'ZJ' }
];

export interface VisaClass {
  type: string;
  description: string;
}

export const visaClasses = [
  { type: 'B2', description: 'Non-Immigrant (Visitor Visa)' },
  { type: 'DE', description: 'Parolee (Deferred Inspection)' },
  { type: 'DA', description: 'Advance Parolee (District Auth)' }
];

export const acceptedFiles = [
  'image/img',
  'image/png',
  'application/pdf',
  'application/msword',
  'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
];
