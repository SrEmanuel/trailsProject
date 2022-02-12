import { NavBar } from '../../components/Navbar';
import { Paginator } from '../../components/Paginator';
import './styles.scss';

export function ListContent(){
    return(
        <div className="container">
            <NavBar />
            <h1>Trilhas disponíveis</h1>
            <Paginator />
        </div>
    )
}