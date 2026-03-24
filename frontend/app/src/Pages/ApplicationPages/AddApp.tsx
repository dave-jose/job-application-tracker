import { useState } from "react";
import { useNavigate } from "react-router-dom";
import '../../Styles/Home.css';
import Sidebar from "./Components/Sidebar";

interface JobApplication {
  appId: number;
  jobTitle: string;
  companyName: string;
  dateApplied: string;
  appStatus: string;
  interviewStatus: string;
}

export default function AddApplication() {

    // variables
    const [jobTitle, setJobTitle] = useState("");
    const [companyName, setCompName] = useState("");
    const [appStatus, setAppStatus] = useState("");
    const [intStatus, setIntStatus] = useState("");
    const [dateApplied, setDateApplied] = useState(new Date());
    const [error, setError] = useState("")

    const appCheck = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/jat/apps", {
                method: "POST",
                headers: {"Content-Type": "application/json",},
                credentials: "include",
                body: JSON.stringify({ jobTitle: jobTitle, companyName: companyName, appStatus: appStatus, intStatus: intStatus, dateApplied: dateApplied}),
            });

        if (response.ok) {
            alert("Application Added!");
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
            <Sidebar/> 
            <div className="content">
                <h1>Add Application</h1>
                <form onSubmit={appCheck}>
                    <div>
                    <label>Job Title: </label>
                    <input
                        type="text"
                        value={jobTitle}
                        onChange={(e) => setJobTitle(e.target.value)}/>
                    </div>
                    <div>
                    <label>Company Name: </label>
                    <input
                        type="text"
                        value={companyName}
                        onChange={(e) => setCompName(e.target.value)}/>
                    </div>
                    <div>
                    <label>Date Applied: </label>
                    <input
                        type="date"
                        onChange={(e) => setDateApplied( new Date (e.target.value))}/>
                    </div>
                    <div>
                    <label>Application Status: </label>
                    <input 
                        type="text"
                        value={appStatus}
                        onChange={(e) => setAppStatus(e.target.value)}/>
                    </div>
                    <div>
                    <label>Interview Status: </label>
                    <input
                        type="text"
                        value={intStatus}
                        onChange={(e) => setIntStatus(e.target.value) }/>
                    </div>
                    <button type="submit">Add Application</button>
                </form> 
            </div>
        </div>

    );


    

}