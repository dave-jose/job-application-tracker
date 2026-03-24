import { useState} from "react";
import { useNavigate } from "react-router-dom";

export default function Logout() {

    const [error, setError] = useState("")
    const navigate = useNavigate();

    const logout = async (e: React.FormEvent) => {
      e.preventDefault();
      try {
        const response = await fetch("http://localhost:8080/api/jat/logout", {
        method: "POST",
        credentials: "include",
        });

        navigate("/");

      } catch (err: any) {
        setError("Netowrk/Server Error" + err.message);
      }

    }

    return <button onClick={logout}>Logout</button>;

}