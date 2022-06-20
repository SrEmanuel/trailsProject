import { Formik } from "formik";
import { useState } from "react";
import { FiEdit, FiSave } from "react-icons/fi";
import "./styles.scss";
import * as Yup from 'yup';

interface Props {
  label: string;
  value: string;
  onSubmit: (value: string) => void;
  type?: string;
  validator?:  Yup.ObjectSchema<any>
}

export function Editable({ label, type, validator, value, onSubmit }: Props) {
  const [isEdittingEnabled, setIsEditingEnabled] = useState<boolean>(false);
  return (
    <div className="editable">
      <span className="label">{label}</span>
      {isEdittingEnabled ? (
        <div className="row-wrapper">
          <Formik initialValues={{ value: '' }} onSubmit={({ value }) => console.log(value) } validationSchema={validator}>
            {({ handleChange, handleSubmit, errors, touched }) => (
              <>
                <input type={type ? type : "text"} name="value" onChange={handleChange} defaultValue={value} />
                {errors.value && touched.value && (
                  <span className="error text">{errors.value}</span>
                )}
                <FiSave
                  color="var(--grey)"
                  size={20}
                  onClick={() => {
                    handleSubmit();
                    setIsEditingEnabled(false);
                  }}
                />
              </>
            )}
          </Formik>
        </div>
      ) : (
        <div className="row-wrapper">
          <span className="value">{value}</span>
          <FiEdit
            color="var(--grey)"
            size={20}
            onClick={() => setIsEditingEnabled(true)}
          />
        </div>
      )}
    </div>
  );
}
