export function decodeToken() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload; 
  } catch (err) {
    console.error("Erreur décodage token", err);
    return null;
  }
}
