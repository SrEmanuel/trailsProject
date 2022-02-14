import { BrowserRouter, Route, Routes as RoutesContainer } from 'react-router-dom';
import { Home } from './pages/Landing';
import { ListContent } from './pages/ListContent';
import { Login } from './pages/Login';

export function Routes(){
    return(
        <BrowserRouter>
            <RoutesContainer>
             <Route path="/" element={<Home></Home>}  />
             <Route path="/login" element={<Login></Login>} />
             <Route path="/cursos" element={<ListContent></ListContent>} />
             <Route path="/cursos/:name" element={<ListContent></ListContent>} />
            </RoutesContainer>
        </BrowserRouter>
    )
}