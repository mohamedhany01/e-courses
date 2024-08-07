import { useParams } from "react-router-dom/cjs/react-router-dom.min";


function MovieDetails({ movies }) {
  const { movieId } = useParams();
  const movie = movies.find(m => m.id === parseInt(movieId, 10));

  if (!movie) {
    return <h1>Movie Not Found</h1>;
  }

  const { title, description } = movie;

  return (
    <div className='comp purple'>
      <h1>{title}</h1>
      <p>{description}</p>
    </div>
  );
}

export default MovieDetails;