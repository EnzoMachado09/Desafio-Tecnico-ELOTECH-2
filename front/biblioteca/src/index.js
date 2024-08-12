import React from 'react';
import ReactDOM from 'react-dom/client';
import Home from './rotas/Home';
import { createGlobalStyle } from 'styled-components';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './componentes/Header';
import Usuarios from './rotas/Usuarios';
import Login from './rotas/Login';
import Livros from './rotas/Livros';
import Recomendacoes from './rotas/Recomendacoes';
import Emprestimos from './rotas/Emprestimos';
import GoogleBooks from './rotas/GoogleBooks';

const GlobalStyle = createGlobalStyle`
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

code {
  font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
    monospace;
}

li {
    list-style: none;
}
`

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <GlobalStyle />
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path='/emprestimos' element={<Emprestimos />} />
        <Route path="/usuarios" element={< Usuarios />} />
        <Route path="/login" element={< Login />} />
        <Route path="/livros" element={< Livros />} />
        <Route path="/recomendacoes" element={< Recomendacoes />} />
        <Route path="/googlebooks" element={< GoogleBooks />} />
        <Route path="/" element={<Home />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
)