export interface UserRoles {
  id: number;
  roleName: string;
  description: string;
}

export interface AppPersons {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  active: boolean;
  email: string;
  role: UserRoles;
  previousLoginDate: number;
}
