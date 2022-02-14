import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Trail } from "../../components/Trail";
import "./styles.scss";

export function ListContent() {
  const trails = [1, 2, 3, 4 , 5, 6 , 7, 8, 9, 10, 11, 12];

  return (
    <div className="container">
      <NavBar />
      <h1>Trilhas disponíveis</h1>
      <Paginator />
      <div className="trails-grid-container">
        { trails.map((trail, index ) =>(
            <Trail color={index % 3 === 0? 'var(--green)' : index % 3 === 1? 'var(--red)' : 'vaR(--purple)' } />
        )) }
      </div>
    </div>
  );
}
