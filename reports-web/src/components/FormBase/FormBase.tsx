import { Formik, FormikConfig } from 'formik';
import React, { PropsWithChildren, ReactElement } from 'react';

function FormBase<T>({
  onSubmit,
  initialValues,
  validationSchema,
  children,
  ...formikProps
}: PropsWithChildren<FormikConfig<T>>): ReactElement {
  return (
    <Formik
      {...formikProps}
      onSubmit={(values, helpers) =>
        onSubmit(validationSchema.cast(values), helpers)
      }
      initialValues={initialValues}
      validationSchema={validationSchema}
      validateOnMount
    >
      {({ handleSubmit }) => (
        <form onSubmit={handleSubmit} data-testid="form-main">
          {children}
        </form>
      )}
    </Formik>
  );
}

export default FormBase;
