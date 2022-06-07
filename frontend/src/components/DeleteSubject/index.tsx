import { FormEvent } from "react";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
  onConfirm: (event: FormEvent) => void;
  message: string;
  confirmText: string;
  cancelText: string;
}

export function ConfirmationModal({isVisible, message, confirmText, cancelText , setIsVisible, onConfirm }: Props) {
  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <h2>{message}</h2>
        <div className="btn-container">
          <button type="button" onClick={() => setIsVisible(false)}>
            {cancelText}
          </button>
          <button type="submit" onClick={onConfirm} >
            {confirmText}
          </button>
        </div>
      </ModalContainer>
    </Overlay>
  );
}
