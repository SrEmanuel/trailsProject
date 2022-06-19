import { useState } from "react";
import { FiEdit, FiSave } from "react-icons/fi";
import "./styles.scss";

interface Props {
  label: string;
  value: string;
  onSubmit: (value: string) => void;
  type?: string;
}

export function Editable({ label, value, onSubmit, type }: Props) {
  const [isEdittingEnabled, setIsEditingEnabled] = useState<boolean>(false);
  return (
   <div className="editable" >
    <span className="label" >{label}</span>
    { isEdittingEnabled? (
      <div className="row-wrapper">
        <input type={ type? type: "text" } value={value} />
        <FiSave color="var(--grey)" size={20} onClick={()=> setIsEditingEnabled(false) } />
      </div>
    ) : (
     <div className="row-wrapper">
       <span className="value" >{value}</span>
       <FiEdit color="var(--grey)" size={20} onClick={() => setIsEditingEnabled(true)} />
     </div>
    ) }
   </div>
  )
}
