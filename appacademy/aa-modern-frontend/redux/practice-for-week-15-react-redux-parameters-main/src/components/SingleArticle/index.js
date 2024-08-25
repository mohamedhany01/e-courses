import { useParams } from 'react-router-dom/cjs/react-router-dom.min';
import './SingleArticle.css';

const SingleArticle = ({ articles }) => {

  const { id: articleID } = useParams();

  const selectedArticle = articles.find(a => a.id === articleID);

  return (
    selectedArticle ? (
      <div className='singleArticle'>
        <h1>{selectedArticle.title}</h1>
        <img
          src={selectedArticle.imageUrl}
          alt={selectedArticle.title}
        />
        <p>{selectedArticle.body}</p>
      </div>
    ) : (
      <div>No article found</div>
    )
  );
};

export default SingleArticle;