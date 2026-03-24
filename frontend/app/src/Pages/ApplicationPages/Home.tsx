import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import '../../Styles/Home.css';
import Logout from "./Components/Logout";
import Sidebar from "./Components/Sidebar";

interface JobApplication {
  appId: number;
  jobTitle: string;
  companyName: string;
  dateApplied: string;
  appStatus: string;
  interviewStatus: string;
}

export default function JobAppList() {

    // variables
    const [jobApps, setJobApps] = useState<JobApplication[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("")
    const navigate = useNavigate();


    // adding an application
    const addApp = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/jat/apps", {
                method: "POST",
                headers: {"Content-Type": "application/json",},
                credentials: "include",
                body: JSON.stringify({  }),
            });

        if (response.ok) {
            navigate("/home");
            alert("Added app!");
        } else {
            const text = await response.text();
            setError("Server response: " + text);
        }
        } catch (err: any) {
            setError("Network/server error: " + err.message);
        }
    };

    useEffect(() => 
      {
        async function fetchList() {
          try {
            const response = await fetch("http://localhost:8080/api/jat/apps", {
            credentials: "include"
            });
            const data = await response.json();
            setJobApps(data)
          } catch (err: any) {
            setError("Network/server error: " + err.message);
          } finally {
            setLoading(false)
          }
        } 
        fetchList();
      }, []); 

      if (loading) return <p>"Loading Applications....."</p>;
      if (error) return <p>{error}</p>

      return (
        <div>
          <Sidebar/>
          <div className="content">
            <h1>JobNest</h1>
          <table>
            <tr>
              <th>Job Title:</th>
              <th>Company Name:</th>
              <th>Date Applied:</th>
              <th>Application Status:</th>
              <th>Interview Status:</th>
            </tr>
            {jobApps.map(app => (
              <tr>
                <td>{app.jobTitle}</td>
                <td>{app.companyName}</td>
                <td>{app.dateApplied}</td>
                <td>{app.appStatus}</td>
                <td>{app.interviewStatus}</td>
              </tr>
            ))}
          </table>
          <br/>
          <Logout/>
          </div>

        </div>

      );








}