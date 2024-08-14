function Message({ size }) {
  console.log('Message', size);

  return (
    <div className="message medium">
      (Oh my! Your bird is naked!)
    </div>
  );
};

export default Message;