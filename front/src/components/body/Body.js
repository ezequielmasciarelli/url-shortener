import React, { useState } from 'react';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Card from "react-bootstrap/Card";

const mystyle = size => {
  return {
    color: "#343a40",
    fontSize: `${size}px`,
    fontFamily: "helvetica"
  }
};

const shrink = (url,alias,setLoading,setResponse,setErr) => {
  setLoading(true)
  const body = { url,alias }
  console.log(`Body: ${JSON.stringify(body)}`)
  fetch('http://localhost:9000/shorten', {
    method: 'POST',
    mode: 'cors',
    cache: "no-cache",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
  })
    .then(response => response.json().then(body => ({ status: response.status, body: body })))
    .then(body => handleResponse(body,setLoading,setResponse,setErr))
}

const handleResponse = (body,setLoading,setResponse,setErr) => {
  if(body.status !== 200) {
    setResponse("Error")
    setErr(body.body.msj)
    setLoading(false)
  }
  else {
    setErr("")
    setLoading(false)
    setResponse(body.body.url)
  }
}

const Body = () => {
  const [wantAlias, setWantAlias] = useState(true);
  const [loading, setLoading] = useState(false);
  const [url, setUrl] = useState("");
  const [alias, setAlias] = useState("");
  const [response, setResponse] = useState("");
  const [error, setErr] = useState("");

  return (
    <Container>
      <Row>
        <Col style={mystyle(38)} className="pt-5 pb-5">
          Short Links, big Results
        </Col>
      </Row>
      <Row>
        <Col style={mystyle(18)} className="pb-5">
            A URL shortener built with powerful tools to help you grow and protect your brand.
        </Col>
      </Row>
      <Row>
        <Col style={mystyle(18)} className="pb-5">
          <Form.Group>
            <Form.Control className="mb-5" value={url} size="lg" type="text" placeholder="Shorten your link" onChange={e => setUrl(e.target.value)} />
            <div className="pb-4">
              <label>
                <Form.Check inline checked={wantAlias} onChange={() => setWantAlias(!wantAlias)}/>
                I want to insert an alias to my url
              </label>
            </div>
            {wantAlias && <Form.Control className="mb-5" value={alias} size="lg" type="text" placeholder="Enter an alias to your URL" onChange={e => setAlias(e.target.value)} />}
            <Button disabled={url === ""} variant="dark" onClick={() => shrink(url,alias,setLoading,setResponse,setErr)}>
              {loading ? <Spinner animation="border" role="status"/> : <span>Shorten</span>}
            </Button>
          </Form.Group>
        </Col>
      </Row>
      {response !== "" &&
      <Row>
        <Col>
          <Card className="text-center">
            {error === "" ?
              (<Card.Body>
                <Card.Title>Your link is ready!</Card.Title>
                <Card.Text>
                  <span>Use this link to access to your page: </span>
                  <a href={response}>{response}</a>
                </Card.Text>
                <Button variant="dark">Copy</Button>
              </Card.Body>)
              :
              (<Card.Body>
                <Card.Title>There was an error</Card.Title>
                <Card.Text>
                  <span>Ups! {error} </span>
                </Card.Text>
              </Card.Body>)
            }

          </Card>
        </Col>
      </Row>
      }

    </Container>
  )

}

export default Body;