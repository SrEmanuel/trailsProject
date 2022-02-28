import { BrowserRouter, Route, Routes as RoutesContainer } from 'react-router-dom';
import { About } from './pages/About';
import { BlogPost } from './pages/BlogPost';
import { Home } from './pages/Landing';
import { ListContent } from './pages/ListContent';
import { Login } from './pages/Login';
import { Register } from './pages/Register';

export function Routes(){
    return(
        <BrowserRouter>
            <RoutesContainer>
             <Route path="/" element={<Home></Home>}  />
             <Route path="/login" element={<Login></Login>} />
             <Route path="/cadastro" element={<Register></Register>} />
             <Route path="/sobre" element={<About></About>} />
             <Route path="/cursos" element={<ListContent></ListContent>} />
             <Route path="/cursos/:courseid" element={<ListContent></ListContent>} />
             <Route path="/cursos/:courseid/:blogid" element={<BlogPost></BlogPost>} />
            </RoutesContainer>
        </BrowserRouter>
    )
}