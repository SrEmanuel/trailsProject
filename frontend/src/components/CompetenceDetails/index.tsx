import { FiX } from "react-icons/fi";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
  name: string;
  description: string;
}

export function CompetenceDetails({isVisible, description, name, setIsVisible  }: Props) {
  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
      <FiX size={24} color="var(--red)" onClick={() => setIsVisible(false)} />
      <div className="competence-data">
        <h3 className="text" >{name}</h3>
        <p className="text">{description}</p>
      </div>
      </ModalContainer>
    </Overlay>
  );
}
