--
-- PostgreSQL database dump
--

-- Dumped from database version 14.3 (Debian 14.3-1.pgdg110+1)
-- Dumped by pg_dump version 14.2

-- Started on 2022-06-07 02:50:57 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3390 (class 0 OID 25312)
-- Dependencies: 211
-- Data for Name: tb_course; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_course (id, created_date, image, last_modified_date, link_name, name) VALUES (1, '2022-05-23 22:29:45.43895', 'default-course.png', '2022-05-23 22:29:45.43895', 'ciencias-natwdwurais', 'Ciencias natwdwurais');
INSERT INTO public.tb_course (id, created_date, image, last_modified_date, link_name, name) VALUES (2, '2022-05-23 22:29:50.004085', 'default-course.png', '2022-05-23 22:29:50.004085', 'ciencias-natawdaawwdwurais', 'Ciencias natawdaawwdwurais');


--
-- TOC entry 3399 (class 0 OID 25355)
-- Dependencies: 220
-- Data for Name: tb_topic; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_topic (id, created_date, last_modified_date, link_name, name, "position", course_id) VALUES (1, '2022-05-31 08:34:38.388633', '2022-05-31 08:34:38.388633', 'o-algoritimo-feito-para-todos-nos', 'O algorítimo feito para todos nós', 45, 1);
INSERT INTO public.tb_topic (id, created_date, last_modified_date, link_name, name, "position", course_id) VALUES (2, '2022-06-05 16:20:44.042379', '2022-06-05 16:20:44.042379', 'o-algoritimo-feit2dwo-para-todos-nos', 'O algorítimo feit2dwo para todos nós', 45, 1);


--
-- TOC entry 3397 (class 0 OID 25347)
-- Dependencies: 218
-- Data for Name: tb_subject; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_subject (id, created_date, grade, html_content, image, last_modified_date, link_name, name, "position", topic_id) VALUES (3, '2022-06-05 16:21:02.020924', '3º ano', '<img src={image} alt='''' /> <h2>Introdução</h2> <p> A Grécia Antiga faz parte da chamada <strong> <Link to=''#''>Antiguidade Ocidental</Link> </strong> , ou Clássica. Os gregos se instalaram principalmente na península Balcânica, na região sudeste da Europa, banhada pelos mares Egeu e Jônio. </p> <p> Ao contrário de outros impérios antigos, a civilização grega não criou uma unidade política e administrativa, mas se <strong> organizou em cidades-estados</strong> (ou póleis), com autonomia e governos próprios. Apesar de não estarem unificados, os gregos tinham a origem em comum e compartilhavam muitos aspectos culturais, estabelecendo diversos tipos de relações entre si. A história da Grécia Antiga é dividida em cinco períodos: </p> <p>A história da Grécia Antiga é dividida em cinco períodos:</p> <ul> <li>Pré-Homérico;</li> <li>Homérico;</li> <li>Arcaico;</li> <li>Clássico;</li> <li>Helenístico.</li> </ul> <p> Tanto o período Homérico quanto o seu anterior são referências a Homero, autor das obras “Ilíada” e “Odisseia”. Essas obras se dedicam a narrar, respectivamente, a Guerra de Tróia, disputada entre gregos e troianos; e o retorno de Ulisses (Odisseu) da guerra. </p> <p> Com isso, se constituem como importantes fontes para compreender a ocupação e colonização das áreas próximas ao mar Egeu, e contribuíram para estabelecer vínculos de origem e culturais entre o povo grego. </p> <h2>Pré-Homérico (XX – XII a.C.)</h2> <p> Esse é o período em que, por volta de 2.000 a.C., diversos povos indo-europeus, entre eles os aqueus, os jônios e os eólios, se estabelecem na península Balcânica a partir de diversos fluxos migratórios. </p> <p> Últimos a chegarem nos Balcãs, os dórios (1.200 a.C.) conseguem dominar a cidade de Micenas e passam a ter o controle da região do Peloponeso. Diante do domínio dos dórios, outros povos se dispersam e passam a povoar outras regiões do litoral grego e da Ásia Menor, no que ficou conhecido como primeira diáspora grega. </p> <h2>Homérico (XII – VIII a.C.)</h2> <p> Após a diáspora, os gregos se espalharam pela região balcânica e se organizaram em pequenas unidades produtivas chamadas “genos”. Baseados na propriedade coletiva da terra, os genos eram formados por grandes famílias, sob o comando de um líder, o “pater”, que possuía diversas funções comunitárias. </p> <p> Com o tempo, essas unidades se tornaram incapazes de abastecer toda a população que vivia dela e começaram a se desagregar. A migração em busca de melhores condições de sobrevivência provocaria a segunda diáspora grega, que desta vez atingiu áreas próximas à península Itálica e ao mar Negro.{'' ''} </p> <h2>Arcaico (VIII – VI a.C.)</h2> <p> Com a crise e declínio dos genos, os antigos “paters” passaram a distribuir suas terras, anteriormente propriedades comunais. Com a distribuição desigual da propriedade privada, surge a classe dos aristocratas e começa a formação do que daria origem às cidades-estados gregas. </p> <p> Principais unidades político-administrativas do povo grego, as cidades-estados passaram a desenvolver modelos próprios de organização interna e de governo. Os dois casos modelos são Atenas e Esparta. </p> <p> É preciso levar em conta que as cidades-estados existiram por centenas de anos e, nesse período, houve variações nos modelos de governo e sociedade. Contudo, essas generalizações ajudam a evidenciar algumas das peculiaridades de cada cidade-estado. </p> <p> Nesse período, também foram criados os Jogos Olímpicos, que reuniam cidadãos de toda a Grécia na pólis de Olímpia, para disputas desportivas em homenagem a Zeus, principal divindade grega. </p> <h3>Atenas</h3> <p> Em linhas gerais, Atenas, embora tivesse uma produção importante de alguns produtos agrícolas, estava voltada ao comércio, em razão das poucas terras férteis disponíveis. Isso contribuiu para que se tornasse uma cidade pluricultural e voltada às atividades intelectuais. Exemplos disso são a filosofia e o teatro, que tiveram seu berço em Atenas. </p> <p> Ao longo de sua história, Atenas passou por vários tipos de governo (monarquia, oligarquia, tirania), mas o principal sistema de governo desenvolvido pelos atenienses foi a democracia, caracterizada pela participação dos cidadãos (exclusivamente homens, filhos de atenienses, livres e maiores de idade) na tomada de decisões políticas. </p> <h3>Esparta</h3> <p> Esparta, por outro lado, estava localizada em uma região de terras férteis, a península do Peloponeso, e priorizava a produção agrícola em vez do comércio. </p> <p> Também possuía uma sociedade altamente militarizada, em que todos as crianças do sexo masculino, em perfeitas condições de saúde, recebiam treinamento militar a partir dos 8 anos de idade. Seu governo era oligárquico, ou seja, controlado por um grupo restrito de pessoas das classes elevadas. </p> <h2>Clássico (V – IV a.C.)</h2> <p> De maneira geral, no período Clássico houve a consolidação dos modelos de cidades-estados gregas e, no caso de Atenas, da democracia. </p> <p> Contudo, também foi um período de conflitos, inicialmente marcado pelo ataque do Império Persa à Grécia. Como os persas eram conhecidos pelos gregos como “medos”, esse conflito foi denominado de Guerras Médicas (499 – 449 a.C.). Ao final, os gregos saíram vencedores e limitaram a expansão do Império Persa. </p> <p> O fim das Guerras Médicas marcou um período de hegemonia ateniense, mas que também provocou o acirramento das disputas entre as póleis rivais. Atenas liderava uma confederação de cidades chamada Confederação de Delos, enquanto Esparta liderava a Liga do Peloponeso. </p> <p> Entre 431 e 404 a.C. as cidades-estados estiveram em conflito, alternando períodos de hegemonia. Mas, esses conflitos acabaram provocando o declínio da civilização grega e facilitando a ação de invasores. </p> <h2>Helenístico (III – II a.C.)</h2> <p> Com o enfraquecimento das cidades-estados, a Grécia foi conquistada por Filipe II, rei da Macedônia. O filho de Filipe II, Alexandre, o Grande, havia sido aluno de Aristóteles em Atenas e compartilhava da cultura grega. </p> <p> Ao expandir os domínios do pai, Alexandre não apenas dominou vastas áreas e conquistou outros impérios, como difundiu a cultura grega pelo Oriente. Essa fusão entre a cultura grega e a cultura oriental, promovida pelas expedições de Alexandre, ficou conhecida como Helenismo. </p>', 'default-subject.png', '2022-06-05 16:21:02.020924', 'como-aplicar-aws-matematica-xaax-iawdananceira-no-aawdawdawwdawdacotiaaaadiadwwwno-aplicado-do-ano', 'Como aplicar aws matemática xaaXiAWDAnanceira no AAWDAWDAWWDAWDAcotiaaaadiadwwwno aplicado do ano', 1, 2);
INSERT INTO public.tb_subject (id, created_date, grade, html_content, image, last_modified_date, link_name, name, "position", topic_id) VALUES (4, '2022-06-06 22:32:42.004519', '3º ano', '<img src={image} alt='''' /> <h2>Introdução</h2> <p> A Grécia Antiga faz parte da chamada <strong> <Link to=''#''>Antiguidade Ocidental</Link> </strong> , ou Clássica. Os gregos se instalaram principalmente na península Balcânica, na região sudeste da Europa, banhada pelos mares Egeu e Jônio. </p> <p> Ao contrário de outros impérios antigos, a civilização grega não criou uma unidade política e administrativa, mas se <strong> organizou em cidades-estados</strong> (ou póleis), com autonomia e governos próprios. Apesar de não estarem unificados, os gregos tinham a origem em comum e compartilhavam muitos aspectos culturais, estabelecendo diversos tipos de relações entre si. A história da Grécia Antiga é dividida em cinco períodos: </p> <p>A história da Grécia Antiga é dividida em cinco períodos:</p> <ul> <li>Pré-Homérico;</li> <li>Homérico;</li> <li>Arcaico;</li> <li>Clássico;</li> <li>Helenístico.</li> </ul> <p> Tanto o período Homérico quanto o seu anterior são referências a Homero, autor das obras “Ilíada” e “Odisseia”. Essas obras se dedicam a narrar, respectivamente, a Guerra de Tróia, disputada entre gregos e troianos; e o retorno de Ulisses (Odisseu) da guerra. </p> <p> Com isso, se constituem como importantes fontes para compreender a ocupação e colonização das áreas próximas ao mar Egeu, e contribuíram para estabelecer vínculos de origem e culturais entre o povo grego. </p> <h2>Pré-Homérico (XX – XII a.C.)</h2> <p> Esse é o período em que, por volta de 2.000 a.C., diversos povos indo-europeus, entre eles os aqueus, os jônios e os eólios, se estabelecem na península Balcânica a partir de diversos fluxos migratórios. </p> <p> Últimos a chegarem nos Balcãs, os dórios (1.200 a.C.) conseguem dominar a cidade de Micenas e passam a ter o controle da região do Peloponeso. Diante do domínio dos dórios, outros povos se dispersam e passam a povoar outras regiões do litoral grego e da Ásia Menor, no que ficou conhecido como primeira diáspora grega. </p> <h2>Homérico (XII – VIII a.C.)</h2> <p> Após a diáspora, os gregos se espalharam pela região balcânica e se organizaram em pequenas unidades produtivas chamadas “genos”. Baseados na propriedade coletiva da terra, os genos eram formados por grandes famílias, sob o comando de um líder, o “pater”, que possuía diversas funções comunitárias. </p> <p> Com o tempo, essas unidades se tornaram incapazes de abastecer toda a população que vivia dela e começaram a se desagregar. A migração em busca de melhores condições de sobrevivência provocaria a segunda diáspora grega, que desta vez atingiu áreas próximas à península Itálica e ao mar Negro.{'' ''} </p> <h2>Arcaico (VIII – VI a.C.)</h2> <p> Com a crise e declínio dos genos, os antigos “paters” passaram a distribuir suas terras, anteriormente propriedades comunais. Com a distribuição desigual da propriedade privada, surge a classe dos aristocratas e começa a formação do que daria origem às cidades-estados gregas. </p> <p> Principais unidades político-administrativas do povo grego, as cidades-estados passaram a desenvolver modelos próprios de organização interna e de governo. Os dois casos modelos são Atenas e Esparta. </p> <p> É preciso levar em conta que as cidades-estados existiram por centenas de anos e, nesse período, houve variações nos modelos de governo e sociedade. Contudo, essas generalizações ajudam a evidenciar algumas das peculiaridades de cada cidade-estado. </p> <p> Nesse período, também foram criados os Jogos Olímpicos, que reuniam cidadãos de toda a Grécia na pólis de Olímpia, para disputas desportivas em homenagem a Zeus, principal divindade grega. </p> <h3>Atenas</h3> <p> Em linhas gerais, Atenas, embora tivesse uma produção importante de alguns produtos agrícolas, estava voltada ao comércio, em razão das poucas terras férteis disponíveis. Isso contribuiu para que se tornasse uma cidade pluricultural e voltada às atividades intelectuais. Exemplos disso são a filosofia e o teatro, que tiveram seu berço em Atenas. </p> <p> Ao longo de sua história, Atenas passou por vários tipos de governo (monarquia, oligarquia, tirania), mas o principal sistema de governo desenvolvido pelos atenienses foi a democracia, caracterizada pela participação dos cidadãos (exclusivamente homens, filhos de atenienses, livres e maiores de idade) na tomada de decisões políticas. </p> <h3>Esparta</h3> <p> Esparta, por outro lado, estava localizada em uma região de terras férteis, a península do Peloponeso, e priorizava a produção agrícola em vez do comércio. </p> <p> Também possuía uma sociedade altamente militarizada, em que todos as crianças do sexo masculino, em perfeitas condições de saúde, recebiam treinamento militar a partir dos 8 anos de idade. Seu governo era oligárquico, ou seja, controlado por um grupo restrito de pessoas das classes elevadas. </p> <h2>Clássico (V – IV a.C.)</h2> <p> De maneira geral, no período Clássico houve a consolidação dos modelos de cidades-estados gregas e, no caso de Atenas, da democracia. </p> <p> Contudo, também foi um período de conflitos, inicialmente marcado pelo ataque do Império Persa à Grécia. Como os persas eram conhecidos pelos gregos como “medos”, esse conflito foi denominado de Guerras Médicas (499 – 449 a.C.). Ao final, os gregos saíram vencedores e limitaram a expansão do Império Persa. </p> <p> O fim das Guerras Médicas marcou um período de hegemonia ateniense, mas que também provocou o acirramento das disputas entre as póleis rivais. Atenas liderava uma confederação de cidades chamada Confederação de Delos, enquanto Esparta liderava a Liga do Peloponeso. </p> <p> Entre 431 e 404 a.C. as cidades-estados estiveram em conflito, alternando períodos de hegemonia. Mas, esses conflitos acabaram provocando o declínio da civilização grega e facilitando a ação de invasores. </p> <h2>Helenístico (III – II a.C.)</h2> <p> Com o enfraquecimento das cidades-estados, a Grécia foi conquistada por Filipe II, rei da Macedônia. O filho de Filipe II, Alexandre, o Grande, havia sido aluno de Aristóteles em Atenas e compartilhava da cultura grega. </p> <p> Ao expandir os domínios do pai, Alexandre não apenas dominou vastas áreas e conquistou outros impérios, como difundiu a cultura grega pelo Oriente. Essa fusão entre a cultura grega e a cultura oriental, promovida pelas expedições de Alexandre, ficou conhecida como Helenismo. </p>', 'default-subject.png', '2022-06-06 22:32:42.004519', 'como-aplicar-aws-matwwwematica-xaax-iawdananceira-no-aawdawdawwdawdacotiaaaadiadwwwno-aplicado-do-ano', 'Como aplicar aws matwwwemática xaaXiAWDAnanceira no AAWDAWDAWWDAWDAcotiaaaadiadwwwno aplicado do ano', 1, 2);
INSERT INTO public.tb_subject (id, created_date, grade, html_content, image, last_modified_date, link_name, name, "position", topic_id) VALUES (1, '2022-05-31 08:34:51.749735', '3º ano', '<img src={image} alt='''' /> <h2>Introdução</h2> <p> A Grécia Antiga faz parte da chamada <strong> <Link to=''#''>Antiguidade Ocidental</Link> </strong> , ou Clássica. Os gregos se instalaram principalmente na península Balcânica, na região sudeste da Europa, banhada pelos mares Egeu e Jônio. </p> <p> Ao contrário de outros impérios antigos, a civilização grega não criou uma unidade política e administrativa, mas se <strong> organizou em cidades-estados</strong> (ou póleis), com autonomia e governos próprios. Apesar de não estarem unificados, os gregos tinham a origem em comum e compartilhavam muitos aspectos culturais, estabelecendo diversos tipos de relações entre si. A história da Grécia Antiga é dividida em cinco períodos: </p> <p>A história da Grécia Antiga é dividida em cinco períodos:</p> <ul> <li>Pré-Homérico;</li> <li>Homérico;</li> <li>Arcaico;</li> <li>Clássico;</li> <li>Helenístico.</li> </ul> <p> Tanto o período Homérico quanto o seu anterior são referências a Homero, autor das obras “Ilíada” e “Odisseia”. Essas obras se dedicam a narrar, respectivamente, a Guerra de Tróia, disputada entre gregos e troianos; e o retorno de Ulisses (Odisseu) da guerra. </p> <p> Com isso, se constituem como importantes fontes para compreender a ocupação e colonização das áreas próximas ao mar Egeu, e contribuíram para estabelecer vínculos de origem e culturais entre o povo grego. </p> <h2>Pré-Homérico (XX – XII a.C.)</h2> <p> Esse é o período em que, por volta de 2.000 a.C., diversos povos indo-europeus, entre eles os aqueus, os jônios e os eólios, se estabelecem na península Balcânica a partir de diversos fluxos migratórios. </p> <p> Últimos a chegarem nos Balcãs, os dórios (1.200 a.C.) conseguem dominar a cidade de Micenas e passam a ter o controle da região do Peloponeso. Diante do domínio dos dórios, outros povos se dispersam e passam a povoar outras regiões do litoral grego e da Ásia Menor, no que ficou conhecido como primeira diáspora grega. </p> <h2>Homérico (XII – VIII a.C.)</h2> <p> Após a diáspora, os gregos se espalharam pela região balcânica e se organizaram em pequenas unidades produtivas chamadas “genos”. Baseados na propriedade coletiva da terra, os genos eram formados por grandes famílias, sob o comando de um líder, o “pater”, que possuía diversas funções comunitárias. </p> <p> Com o tempo, essas unidades se tornaram incapazes de abastecer toda a população que vivia dela e começaram a se desagregar. A migração em busca de melhores condições de sobrevivência provocaria a segunda diáspora grega, que desta vez atingiu áreas próximas à península Itálica e ao mar Negro.{'' ''} </p> <h2>Arcaico (VIII – VI a.C.)</h2> <p> Com a crise e declínio dos genos, os antigos “paters” passaram a distribuir suas terras, anteriormente propriedades comunais. Com a distribuição desigual da propriedade privada, surge a classe dos aristocratas e começa a formação do que daria origem às cidades-estados gregas. </p> <p> Principais unidades político-administrativas do povo grego, as cidades-estados passaram a desenvolver modelos próprios de organização interna e de governo. Os dois casos modelos são Atenas e Esparta. </p> <p> É preciso levar em conta que as cidades-estados existiram por centenas de anos e, nesse período, houve variações nos modelos de governo e sociedade. Contudo, essas generalizações ajudam a evidenciar algumas das peculiaridades de cada cidade-estado. </p> <p> Nesse período, também foram criados os Jogos Olímpicos, que reuniam cidadãos de toda a Grécia na pólis de Olímpia, para disputas desportivas em homenagem a Zeus, principal divindade grega. </p> <h3>Atenas</h3> <p> Em linhas gerais, Atenas, embora tivesse uma produção importante de alguns produtos agrícolas, estava voltada ao comércio, em razão das poucas terras férteis disponíveis. Isso contribuiu para que se tornasse uma cidade pluricultural e voltada às atividades intelectuais. Exemplos disso são a filosofia e o teatro, que tiveram seu berço em Atenas. </p> <p> Ao longo de sua história, Atenas passou por vários tipos de governo (monarquia, oligarquia, tirania), mas o principal sistema de governo desenvolvido pelos atenienses foi a democracia, caracterizada pela participação dos cidadãos (exclusivamente homens, filhos de atenienses, livres e maiores de idade) na tomada de decisões políticas. </p> <h3>Esparta</h3> <p> Esparta, por outro lado, estava localizada em uma região de terras férteis, a península do Peloponeso, e priorizava a produção agrícola em vez do comércio. </p> <p> Também possuía uma sociedade altamente militarizada, em que todos as crianças do sexo masculino, em perfeitas condições de saúde, recebiam treinamento militar a partir dos 8 anos de idade. Seu governo era oligárquico, ou seja, controlado por um grupo restrito de pessoas das classes elevadas. </p> <h2>Clássico (V – IV a.C.)</h2> <p> De maneira geral, no período Clássico houve a consolidação dos modelos de cidades-estados gregas e, no caso de Atenas, da democracia. </p> <p> Contudo, também foi um período de conflitos, inicialmente marcado pelo ataque do Império Persa à Grécia. Como os persas eram conhecidos pelos gregos como “medos”, esse conflito foi denominado de Guerras Médicas (499 – 449 a.C.). Ao final, os gregos saíram vencedores e limitaram a expansão do Império Persa. </p> <p> O fim das Guerras Médicas marcou um período de hegemonia ateniense, mas que também provocou o acirramento das disputas entre as póleis rivais. Atenas liderava uma confederação de cidades chamada Confederação de Delos, enquanto Esparta liderava a Liga do Peloponeso. </p> <p> Entre 431 e 404 a.C. as cidades-estados estiveram em conflito, alternando períodos de hegemonia. Mas, esses conflitos acabaram provocando o declínio da civilização grega e facilitando a ação de invasores. </p> <h2>Helenístico (III – II a.C.)</h2> <p> Com o enfraquecimento das cidades-estados, a Grécia foi conquistada por Filipe II, rei da Macedônia. O filho de Filipe II, Alexandre, o Grande, havia sido aluno de Aristóteles em Atenas e compartilhava da cultura grega. </p> <p> Ao expandir os domínios do pai, Alexandre não apenas dominou vastas áreas e conquistou outros impérios, como difundiu a cultura grega pelo Oriente. Essa fusão entre a cultura grega e a cultura oriental, promovida pelas expedições de Alexandre, ficou conhecida como Helenismo. </p>', 'default-subject.png', '2022-06-06 22:59:44.516312', 'oipopjiii', 'Oipopjiii', 1, 1);
INSERT INTO public.tb_subject (id, created_date, grade, html_content, image, last_modified_date, link_name, name, "position", topic_id) VALUES (7, '2022-06-06 23:02:29.566487', '3º ano', '<img src={image} alt='''' /> <h2>Introdução</h2> <p> A Grécia Antiga faz parte da chamada <strong> <Link to=''#''>Antiguidade Ocidental</Link> </strong> , ou Clássica. Os gregos se instalaram principalmente na península Balcânica, na região sudeste da Europa, banhada pelos mares Egeu e Jônio. </p> <p> Ao contrário de outros impérios antigos, a civilização grega não criou uma unidade política e administrativa, mas se <strong> organizou em cidades-estados</strong> (ou póleis), com autonomia e governos próprios. Apesar de não estarem unificados, os gregos tinham a origem em comum e compartilhavam muitos aspectos culturais, estabelecendo diversos tipos de relações entre si. A história da Grécia Antiga é dividida em cinco períodos: </p> <p>A história da Grécia Antiga é dividida em cinco períodos:</p> <ul> <li>Pré-Homérico;</li> <li>Homérico;</li> <li>Arcaico;</li> <li>Clássico;</li> <li>Helenístico.</li> </ul> <p> Tanto o período Homérico quanto o seu anterior são referências a Homero, autor das obras “Ilíada” e “Odisseia”. Essas obras se dedicam a narrar, respectivamente, a Guerra de Tróia, disputada entre gregos e troianos; e o retorno de Ulisses (Odisseu) da guerra. </p> <p> Com isso, se constituem como importantes fontes para compreender a ocupação e colonização das áreas próximas ao mar Egeu, e contribuíram para estabelecer vínculos de origem e culturais entre o povo grego. </p> <h2>Pré-Homérico (XX – XII a.C.)</h2> <p> Esse é o período em que, por volta de 2.000 a.C., diversos povos indo-europeus, entre eles os aqueus, os jônios e os eólios, se estabelecem na península Balcânica a partir de diversos fluxos migratórios. </p> <p> Últimos a chegarem nos Balcãs, os dórios (1.200 a.C.) conseguem dominar a cidade de Micenas e passam a ter o controle da região do Peloponeso. Diante do domínio dos dórios, outros povos se dispersam e passam a povoar outras regiões do litoral grego e da Ásia Menor, no que ficou conhecido como primeira diáspora grega. </p> <h2>Homérico (XII – VIII a.C.)</h2> <p> Após a diáspora, os gregos se espalharam pela região balcânica e se organizaram em pequenas unidades produtivas chamadas “genos”. Baseados na propriedade coletiva da terra, os genos eram formados por grandes famílias, sob o comando de um líder, o “pater”, que possuía diversas funções comunitárias. </p> <p> Com o tempo, essas unidades se tornaram incapazes de abastecer toda a população que vivia dela e começaram a se desagregar. A migração em busca de melhores condições de sobrevivência provocaria a segunda diáspora grega, que desta vez atingiu áreas próximas à península Itálica e ao mar Negro.{'' ''} </p> <h2>Arcaico (VIII – VI a.C.)</h2> <p> Com a crise e declínio dos genos, os antigos “paters” passaram a distribuir suas terras, anteriormente propriedades comunais. Com a distribuição desigual da propriedade privada, surge a classe dos aristocratas e começa a formação do que daria origem às cidades-estados gregas. </p> <p> Principais unidades político-administrativas do povo grego, as cidades-estados passaram a desenvolver modelos próprios de organização interna e de governo. Os dois casos modelos são Atenas e Esparta. </p> <p> É preciso levar em conta que as cidades-estados existiram por centenas de anos e, nesse período, houve variações nos modelos de governo e sociedade. Contudo, essas generalizações ajudam a evidenciar algumas das peculiaridades de cada cidade-estado. </p> <p> Nesse período, também foram criados os Jogos Olímpicos, que reuniam cidadãos de toda a Grécia na pólis de Olímpia, para disputas desportivas em homenagem a Zeus, principal divindade grega. </p> <h3>Atenas</h3> <p> Em linhas gerais, Atenas, embora tivesse uma produção importante de alguns produtos agrícolas, estava voltada ao comércio, em razão das poucas terras férteis disponíveis. Isso contribuiu para que se tornasse uma cidade pluricultural e voltada às atividades intelectuais. Exemplos disso são a filosofia e o teatro, que tiveram seu berço em Atenas. </p> <p> Ao longo de sua história, Atenas passou por vários tipos de governo (monarquia, oligarquia, tirania), mas o principal sistema de governo desenvolvido pelos atenienses foi a democracia, caracterizada pela participação dos cidadãos (exclusivamente homens, filhos de atenienses, livres e maiores de idade) na tomada de decisões políticas. </p> <h3>Esparta</h3> <p> Esparta, por outro lado, estava localizada em uma região de terras férteis, a península do Peloponeso, e priorizava a produção agrícola em vez do comércio. </p> <p> Também possuía uma sociedade altamente militarizada, em que todos as crianças do sexo masculino, em perfeitas condições de saúde, recebiam treinamento militar a partir dos 8 anos de idade. Seu governo era oligárquico, ou seja, controlado por um grupo restrito de pessoas das classes elevadas. </p> <h2>Clássico (V – IV a.C.)</h2> <p> De maneira geral, no período Clássico houve a consolidação dos modelos de cidades-estados gregas e, no caso de Atenas, da democracia. </p> <p> Contudo, também foi um período de conflitos, inicialmente marcado pelo ataque do Império Persa à Grécia. Como os persas eram conhecidos pelos gregos como “medos”, esse conflito foi denominado de Guerras Médicas (499 – 449 a.C.). Ao final, os gregos saíram vencedores e limitaram a expansão do Império Persa. </p> <p> O fim das Guerras Médicas marcou um período de hegemonia ateniense, mas que também provocou o acirramento das disputas entre as póleis rivais. Atenas liderava uma confederação de cidades chamada Confederação de Delos, enquanto Esparta liderava a Liga do Peloponeso. </p> <p> Entre 431 e 404 a.C. as cidades-estados estiveram em conflito, alternando períodos de hegemonia. Mas, esses conflitos acabaram provocando o declínio da civilização grega e facilitando a ação de invasores. </p> <h2>Helenístico (III – II a.C.)</h2> <p> Com o enfraquecimento das cidades-estados, a Grécia foi conquistada por Filipe II, rei da Macedônia. O filho de Filipe II, Alexandre, o Grande, havia sido aluno de Aristóteles em Atenas e compartilhava da cultura grega. </p> <p> Ao expandir os domínios do pai, Alexandre não apenas dominou vastas áreas e conquistou outros impérios, como difundiu a cultura grega pelo Oriente. Essa fusão entre a cultura grega e a cultura oriental, promovida pelas expedições de Alexandre, ficou conhecida como Helenismo. </p>', 'default-subject.png', '2022-06-06 23:02:29.566487', 'oi', 'oi', 1, 2);


--
-- TOC entry 3391 (class 0 OID 25319)
-- Dependencies: 212
-- Data for Name: tb_professorsubject; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_professorsubject (id, creation_date, modification_date, subject_id, contributions, user_email, user_name) VALUES (40, '2022-06-05 16:21:02.068291', '2022-06-05 16:21:02.068291', 3, 1, 'emanuelmartins284@gmail.com', 'Emanuel Martins');
INSERT INTO public.tb_professorsubject (id, creation_date, modification_date, subject_id, contributions, user_email, user_name) VALUES (42, '2022-06-06 22:32:42.148996', '2022-06-06 22:32:42.148996', 4, 1, 'emanuelmartins284@gmail.com', 'Emanuel Martins');
INSERT INTO public.tb_professorsubject (id, creation_date, modification_date, subject_id, contributions, user_email, user_name) VALUES (32, '2022-05-31 08:34:51.806154', '2022-06-06 22:59:44.475355', 1, 2, 'emanuelmartins284@gmail.com', 'Emanuel Martins');
INSERT INTO public.tb_professorsubject (id, creation_date, modification_date, subject_id, contributions, user_email, user_name) VALUES (46, '2022-06-06 23:02:29.619565', '2022-06-06 23:02:29.619565', 7, 1, 'emanuelmartins284@gmail.com', 'Emanuel Martins');


--
-- TOC entry 3401 (class 0 OID 25363)
-- Dependencies: 222
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_user (id, created_date, email, last_modified_date, name, password, status) VALUES (1, '2022-05-23 10:06:14.610861', 'emanuelmartins284@gmail.com', '2022-05-23 10:06:14.610861', 'Emanuel Martins', '22cb060df34c600eecb5f4d08304b8289672ec80c9c2fb16ec52a1e21e88124d94c98e0b7dbb3372', true);
INSERT INTO public.tb_user (id, created_date, email, last_modified_date, name, password, status) VALUES (4, '2022-05-30 11:21:00.276924', 'elyeiza@hotmail.com', '2022-05-30 11:21:00.276924', 'Izabella Antunes Jorge', 'd624a513c93d138d1846a6a4826cf1ccb3add5b7ed348ba9e0ebcb1fe6c9521cc467cf1a8aba2f8f', true);


--
-- TOC entry 3392 (class 0 OID 25326)
-- Dependencies: 213
-- Data for Name: tb_profiles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_profiles (user_id, profiles) VALUES (1, 1);
INSERT INTO public.tb_profiles (user_id, profiles) VALUES (1, 2);
INSERT INTO public.tb_profiles (user_id, profiles) VALUES (1, 3);
INSERT INTO public.tb_profiles (user_id, profiles) VALUES (4, 1);


--
-- TOC entry 3394 (class 0 OID 25330)
-- Dependencies: 215
-- Data for Name: tb_recovertoken; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3404 (class 0 OID 41844)
-- Dependencies: 225
-- Data for Name: tb_refreshtoken; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3395 (class 0 OID 25341)
-- Dependencies: 216
-- Data for Name: tb_studentsubject; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (33, '2022-05-31 08:35:01.062874', '2022-05-31 08:35:01.062874', 1, true, 1);
INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (38, '2022-05-31 15:47:26.719461', '2022-06-04 11:22:12.006233', 1, true, 4);
INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (41, '2022-06-05 16:21:26.886187', '2022-06-05 16:21:26.886187', 3, false, 4);
INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (43, '2022-06-06 22:39:24.970303', '2022-06-06 22:39:24.970303', 4, false, 1);
INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (47, '2022-06-06 23:02:40.904281', '2022-06-06 23:03:01.595356', 7, true, 1);
INSERT INTO public.tb_studentsubject (id, creation_date, modification_date, subject_id, completed, user_id) VALUES (48, '2022-06-06 23:03:53.209004', '2022-06-06 23:03:53.209004', 3, false, 1);


--
-- TOC entry 3402 (class 0 OID 33630)
-- Dependencies: 223
-- Data for Name: tb_usercourse; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_usercourse (count_completed, course_id, user_id) VALUES (2, 1, 4);
INSERT INTO public.tb_usercourse (count_completed, course_id, user_id) VALUES (3, 1, 1);


--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 209
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.hibernate_sequence', 48, true);


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 210
-- Name: tb_course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_course_id_seq', 2, true);


--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 214
-- Name: tb_recovertoken_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_recovertoken_id_seq', 1, false);


--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 224
-- Name: tb_refreshtoken_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_refreshtoken_id_seq', 1, false);


--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 217
-- Name: tb_subject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_subject_id_seq', 7, true);


--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 219
-- Name: tb_topic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_topic_id_seq', 2, true);


--
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 221
-- Name: tb_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tb_user_id_seq', 4, true);


-- Completed on 2022-06-07 02:50:57 UTC

--
-- PostgreSQL database dump complete
--

