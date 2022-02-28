import { NavBar } from "../../components/Navbar";
import { WaveContainer } from "../../components/WaveContainer";
import hiking from '../../assets/images/hiking.png';
import "./styles.scss";

export function About() {
  return (
    <WaveContainer>
      <NavBar />
      <main className="about-container">
        <section>
          <h1>A nossa missão</h1>
          <span>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur
            sodales erat eget magna malesuada, ac tempor dolor tempus.
            Suspendisse euismod nec augue vel finibus. Duis mattis nunc vel
            dapibus consequat. Nulla mollis odio tristique finibus hendrerit.
            Vestibulum ante ipsum primis in faucibus orci luctus et ultrices
            posuere cubilia curae; Phasellus iaculis commodo congue. Proin at
            iaculis ligula. Donec vel risus sed nulla fermentum volutpat a vitae
            mauris. Aenean id elit varius, fermentum leo nec, egestas odio. Cras
            fringilla finibus justo.
          </span>

          <img src={hiking} alt="Pessoa fazendo trilha" />

          <h2>Nosso time</h2>
          <div className="colaborators">
            <div>
              <div className="circle"></div>
              <span>Fulano - Dev backend</span>
            </div>
            <div>
            <div className="circle"></div>
              <span>Fulano - Dev frontend</span>
            </div>
            <div>
            <div className="circle"></div>
              <span>Fulano - Dev backend</span>
            </div>
          </div>
        </section>
      </main>
    </WaveContainer>
  );
}
