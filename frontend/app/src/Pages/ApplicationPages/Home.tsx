import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import '../../Styles/Home.css';

interface JobApplication {
  appId: number;
  jobTitle: string;
  companyName: string;
  dateApplied: string;
  appStatus: string;
  interviewStatus: string;
}

export default function JobAppList() {
    const [jobApps, setJobApps] = useState<JobApplication[]>([]);
    const [loading, setLoading] = useState(true);
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
        <button onClick={logout}>Logout</button>
        </div>
      );








}