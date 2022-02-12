import { BrowserRouter, Route, Routes as RoutesContainer } from 'react-router-dom';
import { Home } from './pages/Landing';
import { Login } from './pages/Login';

export function Routes(){
    return(
        <BrowserRouter>
            <RoutesContainer>
             <Route path="/" element={<Home></Home>}  />
             <Route path="/login" element={<Login></Login>} />
            </RoutesContainer>
        </BrowserRouter>
    )
}