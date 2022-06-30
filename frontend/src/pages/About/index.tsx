import { NavBar } from "../../components/Navbar";
import { WaveContainer } from "../../components/WaveContainer";
import dev1 from '../../assets/images/dev1.jpg';
import dev2 from '../../assets/images/dev2.jpeg';
import dev3 from '../../assets/images/dev3.jpeg';
import hiking from "../../assets/images/hiking.png";
import "./styles.scss";

export function About() {
  return (
    <WaveContainer>
      <NavBar />
      <main className="about-container">
        <section>
          <h1>A nossa missão</h1>
          <p>
            Com o advento da internet, houve o surgimento de problemas
            relacionados à disponibilização de conteúdo educacional nas redes de
            computadores. Tal disponibilização é, até hoje, descontrolada e
            muitas vezes insegura, o que afeta os estudantes que desejam acessar
            conteúdos para estudo de forma rápida, segura, organizada e com
            respaldo pedagógico. 
          </p>

          <p>Desta forma, o Projeto Trilhas propôs a criação
            de uma solução para tal problemática, que - por meio de um sistema
            online - dá ferramentas para os professores de uma instituição
            cadastrarem, organizarem e planejarem a disponibilização de
            conteúdos e exercícios aos alunos de forma segura, acessível e
            unificada.</p>

          <img src={hiking} alt="Pessoa fazendo trilha" />

          <h2>Nosso time</h2>
          <div className="colaborators">
            <div>
              <div className="circle">
                  <img src={dev2} alt="Emanuel" />
              </div>
              <span>Emanuel - Dev backend</span>
            </div>
            <div>
              <div className="circle">
                <img src={dev1} alt="Edmarcos" />
              </div>
              <span>Edmarcos - Dev frontend</span>
            </div>
            <div>
              <div className="circle">
              <img src={dev3} alt="Giulian" />
              </div>
              <span>Giulian - Copywritter</span>
            </div>
          </div>
        </section>
      </main>
    </WaveContainer>
  );
}
