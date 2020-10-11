import React from 'react';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import logo from "./logo192.png";

const Header = () => {
  return (
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="#home">
        <img
          alt=""
          src={logo}
          width="30"
          height="30"
          className="d-inline-block align-top"
        />{' '}
        Rub.io</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="#home">Why Rub.io?</Nav.Link>
        <Nav.Link href="#features">Features</Nav.Link>
        <Nav.Link href="#pricing">Pricing</Nav.Link>
        <Nav.Link href="#about">About Me</Nav.Link>
      </Nav>
    </Navbar>
  )

}

export default Header;