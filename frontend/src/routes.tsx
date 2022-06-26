import { BrowserRouter, Route, Routes as RoutesContainer } from 'react-router-dom';
import { PageNotFound } from './pages/404';
import { About } from './pages/About';
import { BlogPost } from './pages/BlogPost';
import { CreateContent } from './pages/Create/CreateContent';
import { CreateCourse } from './pages/Create/CreateCourse';
import { ForgotPassword } from './pages/ForgotPassword';
import { Home } from './pages/Landing';
import { ListContent } from './pages/ListContent';
import { Login } from './pages/Login';
import { PostForm } from './pages/PostForm';
import { Register } from './pages/Register';
import { ResetPassword } from './pages/ResetPassword';

export function Routes(){
    return(
        <BrowserRouter>
            <RoutesContainer>
             <Route path="/" element={<Home></Home>}  />
             <Route path="/login" element={<Login></Login>} />
             <Route path="/cadastro" element={<Register></Register>} />
             <Route path="/sobre" element={<About></About>} />
             <Route path="/nova-senha" element={<ResetPassword></ResetPassword>} />
             <Route path="/esqueci-minha-senha" element={<ForgotPassword></ForgotPassword>} />
             <Route path="/cursos" element={<ListContent></ListContent>} />
             <Route path="/cursos/:coursename" element={<ListContent></ListContent>} />
             <Route path="/cursos/:courseid/:blogtitle" element={<BlogPost></BlogPost>} />
             <Route path="/novo-curso" element={<CreateCourse></CreateCourse>} />
             <Route path="/cursos/adicionar" element={<PostForm></PostForm>} />
             <Route path="/cursos/atualizar/:coursename" element={<CreateCourse></CreateCourse>} />
             <Route path="/cursos/:courseid/:topicId/novo-conteudo" element={<CreateContent></CreateContent>} />
             <Route path="/cursos/:coursename/:topicId/:contentname/atualizar" element={<CreateContent></CreateContent>} />
             <Route path="*" element={<PageNotFound></PageNotFound>} />
            </RoutesContainer>
        </BrowserRouter>
    )
}