import "./styles.scss";
import { ReactComponent as Previous } from "../../assets/images/previous.svg";
import { ReactComponent as Next } from "../../assets/images/next.svg";

interface Props {
  setPage: (arg: number) => void;
  totalPages: number;
  page: number;
}

export function Paginator({ setPage, page, totalPages }: Props) {
  function handlePrevious() {
    if (page > 1) {
      setPage(page - 1);
    }
  }

  function handleNext() {
    if (page < totalPages) {
      setPage(page + 1);
    }
  }

  return (
    <div className="paginator">
      <Previous onClick={ handlePrevious } />
      Página {page} de {totalPages}
      <Next onClick={ handleNext } />
    </div>
  );
}
