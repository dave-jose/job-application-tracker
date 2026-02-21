import { useState } from "react";

export default function Create() {

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("")

    const createCheck = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/jat/create", {
                method: "POST",
                headers: {"Content-Type": "application/json",},
                credentials: "include",
                body: JSON.stringify({ userEmail: email, password: password }),
            });

        if (response.ok) {
            alert("Create successful!");
        } else {
            const text = await response.text();
            setError("Server response: " + text);
        }
        } catch (err: any) {
            setError("Network/server error: " + err.message);
        }
    };

    return (
        <div>
        <h2>Create</h2>

        <form onSubmit={createCheck}>
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

        {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
    }