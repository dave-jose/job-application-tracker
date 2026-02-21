import React from 'react';
import logo from './logo.svg';
import './App.css';
import Login from "./Pages/Login";
import Create from "./Pages/Create";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './Pages/ApplicationPages/Home';

function App() {
  return (
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<Login/>}/>
      <Route path="/create" element={<Create/>}/>
      <Route path="/home" element={<Home/>}/>
    </Routes>
    </BrowserRouter>
  );
}

export default App;
