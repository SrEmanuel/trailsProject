import { BrowserRouter, Route, Routes as RoutesContainer } from 'react-router-dom';
import { Home } from './pages/Landing';

export function Routes(){
    return(
        <BrowserRouter>
            <RoutesContainer>
             <Route path="/" element={<Home></Home>}  />
            </RoutesContainer>
        </BrowserRouter>
    )
}