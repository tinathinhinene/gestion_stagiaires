import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

function SearchPage() {
  const [params] = useSearchParams();
  const query = params.get("q") || "";

  const [results, setResults] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!query) return;

    const token = localStorage.getItem("token");

    fetch(`http://localhost:8021/api/search?q=${encodeURIComponent(query)}`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : "",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setResults(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, [query]);

  if (loading) {
    return <div className="accueil-container">Chargement...</div>;
  }

  if (!results) {
    return <div className="accueil-container">Aucun résultat.</div>;
  }

  return (
    <div className="accueil-container">
      <h1>Résultats pour : "{results.query}"</h1>

      {/* STAGIAIRES */}
      <h2>Stagiaires</h2>
      {results.stagiaires && results.stagiaires.length > 0 ? (
        <ul>
          {results.stagiaires.map((s) => (
            <li key={s.id}>
              {s.prenom} {s.nom} – {s.email}
            </li>
          ))}
        </ul>
      ) : (
        <p>Aucun stagiaire trouvé.</p>
      )}

      {/* FORMATEURS */}
      <h2>Formateurs</h2>
      {results.formateurs && results.formateurs.length > 0 ? (
        <ul>
          {results.formateurs.map((f) => (
            <li key={f.id}>
              {f.prenom} {f.nom}
            </li>
          ))}
        </ul>
      ) : (
        <p>Aucun formateur trouvé.</p>
      )}

      {/* PROJETS */}
      <h2>Projets</h2>
      {results.projets && results.projets.length > 0 ? (
        <ul>
          {results.projets.map((p) => (
            <li key={p.id}>
              <strong>{p.titre}</strong> – {p.description}
            </li>
          ))}
        </ul>
      ) : (
        <p>Aucun projet trouvé.</p>
      )}
    </div>
  );
}

export default SearchPage;
