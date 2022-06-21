import { ReactNode } from "react";
import "./styles.scss";

interface Props {
  children: ReactNode;
  customStyle?: string 
}

export function ModalContainer({ children, customStyle }: Props) {
  return <div className="modal-container" id={customStyle} > {children} </div>;
}
