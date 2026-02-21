import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("")

    const navigate = useNavigate();

    const loginCheck = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/jat/login", {
                method: "POST",
                headers: {"Content-Type": "application/json",},
                credentials: "include",
                body: JSON.stringify({ userEmail: email, password: password }),
        });

      if (response.ok) {
        navigate("/home");
        alert("Login successful!");
      } else {
        setError("Invalid credentials");
      }
    } catch (err: any) {
      setError("Network/server error: " + err.message);
    }
  };

  const createAcc = (e: React.FormEvent) => {
    e.preventDefault();
    navigate("/create");
  }
  
  

  return (
    <div>
      <h2>Login</h2>

      <form onSubmit={loginCheck}>
        <div>
          <label>Email:</label>
          <input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit">Login</button>
      </form>

      <form onSubmit={createAcc}>
        <button type="submit">No account? Create One Here</button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}