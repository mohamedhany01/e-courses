// frontend/src/CreateTweet.js

import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { postTweet } from './store/tweet';

const CreateTweet = () => {
    const [tweet, setTweet] = useState('');
    const dispatch = useDispatch();

    const handleChange = (e) => {
        setTweet(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        dispatch(postTweet({ content: tweet }));
        
        setTweet('');
    };

    return (
        <div>
            <h2>Create a Tweet</h2>
            <form onSubmit={handleSubmit}>
                <textarea
                    value={tweet}
                    onChange={handleChange}
                    placeholder="What's on your mind?"
                    rows="4"
                    cols="50"
                />
                <br />
                <button type="submit">Tweet</button>
            </form>
        </div>
    );
};

export default CreateTweet;
