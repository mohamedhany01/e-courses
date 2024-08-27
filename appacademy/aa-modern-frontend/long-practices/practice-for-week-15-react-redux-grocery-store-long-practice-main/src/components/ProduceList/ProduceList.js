import { useSelector } from 'react-redux';
import ProduceDetails from './ProduceDetails';
import './ProduceList.css';
import { getAllProduce } from '../../store/reducers/produce';

function ProduceList({ onAddItem }) {
  const produceArr = useSelector(getAllProduce);

  return (
    <>
      <h2>All produce</h2>
      {!produceArr.length && <span>No produce available right now.</span>}
      <ul className="produce-list">
        {produceArr.map((p) => (
          <ProduceDetails key={p.id} produce={p} onAddItem={onAddItem}/>
        ))}
      </ul>
    </>
  );
}

export default ProduceList;