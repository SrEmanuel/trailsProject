import { NavBar } from "../../components/Navbar";
import "./styles.scss";
import image from "../../assets/images/subject-example.png";
import { Link } from "react-router-dom";

export function BlogPost() {
  return (
    <div className="container">
      <NavBar />
      <h1>Grécia Antiga</h1>
      <span className="authors" >
        <strong>Autores:</strong>
        João Kleber, Débora Simões, Rosângela Nascimento Tatiane Malheiros
      </span>
      <div className="html-content">
        <img src={image} alt="" />
        <h2>Introdução</h2>
        <p>
          A Grécia Antiga faz parte da chamada
          <strong>
            <Link to="#">Antiguidade Ocidental</Link>
          </strong>
          , ou Clássica. Os gregos se instalaram principalmente na península
          Balcânica, na região sudeste da Europa, banhada pelos mares Egeu e
          Jônio.
        </p>
        <p>
          Ao contrário de outros impérios antigos, a civilização grega não criou
          uma unidade política e administrativa, mas se
          <strong> organizou em cidades-estados</strong> (ou póleis), com
          autonomia e governos próprios. Apesar de não estarem unificados, os
          gregos tinham a origem em comum e compartilhavam muitos aspectos
          culturais, estabelecendo diversos tipos de relações entre si. A
          história da Grécia Antiga é dividida em cinco períodos:
        </p>

        <p>A história da Grécia Antiga é dividida em cinco períodos:</p>

        <ul>
          <li>Pré-Homérico;</li>
          <li>Homérico;</li>
          <li>Arcaico;</li>
          <li>Clássico;</li>
          <li>Helenístico.</li>
        </ul>

        <p>
          Tanto o período Homérico quanto o seu anterior são referências a
          Homero, autor das obras “Ilíada” e “Odisseia”. Essas obras se dedicam
          a narrar, respectivamente, a Guerra de Tróia, disputada entre gregos e
          troianos; e o retorno de Ulisses (Odisseu) da guerra.
        </p>

        <p>
          Com isso, se constituem como importantes fontes para compreender a
          ocupação e colonização das áreas próximas ao mar Egeu, e contribuíram
          para estabelecer vínculos de origem e culturais entre o povo grego.
        </p>

        <h2>Pré-Homérico (XX – XII a.C.)</h2>
        <p>
          Esse é o período em que, por volta de 2.000 a.C., diversos povos
          indo-europeus, entre eles os aqueus, os jônios e os eólios, se
          estabelecem na península Balcânica a partir de diversos fluxos
          migratórios.
        </p>
        <p>
          Últimos a chegarem nos Balcãs, os dórios (1.200 a.C.) conseguem
          dominar a cidade de Micenas e passam a ter o controle da região do
          Peloponeso. Diante do domínio dos dórios, outros povos se dispersam e
          passam a povoar outras regiões do litoral grego e da Ásia Menor, no
          que ficou conhecido como primeira diáspora grega.
        </p>

        <h2>Homérico (XII – VIII a.C.)</h2>

        <p>
          Após a diáspora, os gregos se espalharam pela região balcânica e se
          organizaram em pequenas unidades produtivas chamadas “genos”. Baseados
          na propriedade coletiva da terra, os genos eram formados por grandes
          famílias, sob o comando de um líder, o “pater”, que possuía diversas
          funções comunitárias.
        </p>

        <p>
          Com o tempo, essas unidades se tornaram incapazes de abastecer toda a
          população que vivia dela e começaram a se desagregar. A migração em
          busca de melhores condições de sobrevivência provocaria a segunda
          diáspora grega, que desta vez atingiu áreas próximas à península
          Itálica e ao mar Negro.{" "}
        </p>

        <h2>Arcaico (VIII – VI a.C.)</h2>

        <p>
          Com a crise e declínio dos genos, os antigos “paters” passaram a
          distribuir suas terras, anteriormente propriedades comunais. Com a
          distribuição desigual da propriedade privada, surge a classe dos
          aristocratas e começa a formação do que daria origem às
          cidades-estados gregas.
        </p>

        <p>
          Principais unidades político-administrativas do povo grego, as
          cidades-estados passaram a desenvolver modelos próprios de organização
          interna e de governo. Os dois casos modelos são Atenas e Esparta.
        </p>
        <p>
          É preciso levar em conta que as cidades-estados existiram por centenas
          de anos e, nesse período, houve variações nos modelos de governo e
          sociedade. Contudo, essas generalizações ajudam a evidenciar algumas
          das peculiaridades de cada cidade-estado.
        </p>
        <p>
          Nesse período, também foram criados os Jogos Olímpicos, que reuniam
          cidadãos de toda a Grécia na pólis de Olímpia, para disputas
          desportivas em homenagem a Zeus, principal divindade grega.
        </p>

        <h3>Atenas</h3>

        <p>
          Em linhas gerais, Atenas, embora tivesse uma produção importante de
          alguns produtos agrícolas, estava voltada ao comércio, em razão das
          poucas terras férteis disponíveis. Isso contribuiu para que se
          tornasse uma cidade pluricultural e voltada às atividades
          intelectuais. Exemplos disso são a filosofia e o teatro, que tiveram
          seu berço em Atenas.
        </p>

        <p>
          Ao longo de sua história, Atenas passou por vários tipos de governo
          (monarquia, oligarquia, tirania), mas o principal sistema de governo
          desenvolvido pelos atenienses foi a democracia, caracterizada pela
          participação dos cidadãos (exclusivamente homens, filhos de
          atenienses, livres e maiores de idade) na tomada de decisões
          políticas.
        </p>

        <h3>Esparta</h3>

        <p>
          Esparta, por outro lado, estava localizada em uma região de terras
          férteis, a península do Peloponeso, e priorizava a produção agrícola
          em vez do comércio.
        </p>
        <p>
          Também possuía uma sociedade altamente militarizada, em que todos as
          crianças do sexo masculino, em perfeitas condições de saúde, recebiam
          treinamento militar a partir dos 8 anos de idade. Seu governo era
          oligárquico, ou seja, controlado por um grupo restrito de pessoas das
          classes elevadas.
        </p>

        <h2>Clássico (V – IV a.C.)</h2>

        <p>
          De maneira geral, no período Clássico houve a consolidação dos modelos
          de cidades-estados gregas e, no caso de Atenas, da democracia.
        </p>

        <p>
          Contudo, também foi um período de conflitos, inicialmente marcado pelo
          ataque do Império Persa à Grécia. Como os persas eram conhecidos pelos
          gregos como “medos”, esse conflito foi denominado de Guerras Médicas
          (499 – 449 a.C.). Ao final, os gregos saíram vencedores e limitaram a
          expansão do Império Persa.
        </p>

        <p>
          O fim das Guerras Médicas marcou um período de hegemonia ateniense,
          mas que também provocou o acirramento das disputas entre as póleis
          rivais. Atenas liderava uma confederação de cidades chamada
          Confederação de Delos, enquanto Esparta liderava a Liga do Peloponeso.
        </p>

        <p>
          Entre 431 e 404 a.C. as cidades-estados estiveram em conflito,
          alternando períodos de hegemonia. Mas, esses conflitos acabaram
          provocando o declínio da civilização grega e facilitando a ação de
          invasores.
        </p>

        <h2>Helenístico (III – II a.C.)</h2>

        <p>
          Com o enfraquecimento das cidades-estados, a Grécia foi conquistada
          por Filipe II, rei da Macedônia. O filho de Filipe II, Alexandre, o
          Grande, havia sido aluno de Aristóteles em Atenas e compartilhava da
          cultura grega.
        </p>

        <p>
          Ao expandir os domínios do pai, Alexandre não apenas dominou vastas
          áreas e conquistou outros impérios, como difundiu a cultura grega pelo
          Oriente. Essa fusão entre a cultura grega e a cultura oriental,
          promovida pelas expedições de Alexandre, ficou conhecida como
          Helenismo.
        </p>
      </div>
    </div>
  );
}
