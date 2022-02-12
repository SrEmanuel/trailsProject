import { FiLogIn } from "react-icons/fi";
import { NavBar } from "../../components/Navbar";
import Trails  from '../../assets/images/trails.svg';

import './styles.scss';

export function Home(){
    return(
        <div className="container">
            <NavBar />
            <div className="home-content">
                <section>
                    <h1>Não sabe qual caminho de estudos trilhar?</h1>
                    <span className="text" >Projeto trilhas vai te ajudar!</span>
                    <button>
                        <div>
                        <FiLogIn color="var(--white)" size={24} />
                        </div>
                        Cadastre-se
                    </button>
                </section>
                
            </div>
        </div>
    )
}