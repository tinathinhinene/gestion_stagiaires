import { createContext, useState, useEffect } from "react";
import { decodeToken } from "../utils/jwt";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setUser(decodeToken());
    }
  }, []);

  const login = (token) => {
    localStorage.setItem("token", token);
    setUser(decodeToken());
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
