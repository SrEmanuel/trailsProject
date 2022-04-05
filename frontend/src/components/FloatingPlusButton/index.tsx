import { FiPlus } from "react-icons/fi";
import "./styles.scss";

interface Props {
  onClick: () => void;
}

export function FloatingPlusButton({ onClick }: Props) {
  return (
    <button onClick={onClick} className="floating-btn">
      <FiPlus size={24} color="var(--white)" />
    </button>
  );
}
