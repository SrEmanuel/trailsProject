import { ReactNode } from "react";
import "./styles.scss";

interface Props {
  children: ReactNode;
}

export function ModalContainer({ children }: Props) {
  return <div className="modal-container"> {children} </div>;
}
