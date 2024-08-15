import React, { useEffect, useState } from 'react';

const Component = () => {
    // State for each form field
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [phoneType, setPhoneType] = useState('');
    const [staff, setStaff] = useState('');
    const [bio, setBio] = useState('');
    const [emailNotifications, setEmailNotifications] = useState(false);

    const [hasSubmitted, setHasSubmitted] = useState(false);
    const [validationErrors, setValidationErrors] = useState({});


    const validatePhoneNumber = (number) => {
        // Regular expression for US phone numbers
        const phoneRegex = /^(?:\+1\s?)?(?:\(\d{3}\)|\d{3})(?:[\s.-]?\d{3})(?:[\s.-]?\d{4})$/;
        return phoneRegex.test(number);
    };

    useEffect(() => {

        const errors = {};

        if (!name.length) {
            errors["nameField"] = 'Please enter your Name';
        }

        if (!email.includes("@")) {
            errors['emailField'] = 'Please provide a valid Email';
        }

        if (!validatePhoneNumber(phoneNumber)) {
            errors['phoneNumberField'] = 'Please provide a valid phonenumber';
        }

        if (validatePhoneNumber(phoneNumber) && !phoneType.length) {
            errors['phoneTypeField'] = 'Please provide a phonenumber';
        }

        if (bio.length > 280) {
            errors['bioield'] = `Characters limit is 280, current is ${bio.length}`;
        }

        setValidationErrors(errors);

    }, [name, email, phoneNumber, phoneType, bio]);

    useEffect(() => {
        
        if (!phoneNumber.length) {
            setPhoneType("");
        }
    }, [phoneNumber])

    // Handler functions
    const handleSubmit = (e) => {
        e.preventDefault();

        setHasSubmitted(true);

        // Handle form submission
        console.log(validationErrors);
        console.log({
            name,
            email,
            phoneNumber,
            phoneType,
            staff,
            bio,
            emailNotifications
        });


        if (Object.values(validationErrors).length) {
            return;
        }

        const contactUsInformation = {
            name,
            email,
            phoneNumber,
            phoneType,
            staff,
            bio,
            emailNotifications,
            submittedOn: new Date()
        };


        console.log(JSON.stringify(contactUsInformation));
        setName('');
        setEmail('');
        setPhoneNumber('');
        setPhoneType('');
        setStaff('')
        setBio('');
        setEmailNotifications(false);
        setValidationErrors({});
        setHasSubmitted(false);
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    id="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="e.g., Johan Doe"
                />
                <div className='error'>
                    {hasSubmitted && validationErrors.nameField && `* ${validationErrors.nameField}`}
                </div>
            </div>

            <div>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="e.g., foo@example.com"
                />
                <div className='error'>
                    {hasSubmitted && validationErrors.emailField && `* ${validationErrors.emailField}`}
                </div>
            </div>

            <div>
                <label htmlFor="phoneNumber">Phone Number:</label>
                <input
                    type="tel"
                    id="phoneNumber"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    placeholder="e.g., (123) 456-7890"
                />
                <div className='error'>
                    {hasSubmitted && validationErrors.phoneNumberField && `* ${validationErrors.phoneNumberField}`}
                </div>
            </div>

            <div>
                <label htmlFor="phoneType">Phone Type:</label>
                <select
                    id="phoneType"
                    value={phoneType}
                    onChange={(e) => setPhoneType(e.target.value)}
                    disabled={!phoneNumber}
                >
                    <option value="Home">Home</option>
                    <option value="Work">Work</option>
                    <option value="Mobile">Mobile</option>
                </select>
                <div className='error'>
                    {hasSubmitted && validationErrors.phoneTypeField && `* ${validationErrors.phoneTypeField}`}
                </div>
            </div>

            <div>
                <label>Staff:</label>
                <div>
                    <label>
                        <input
                            type="radio"
                            name="staff"
                            value="Instructor"
                            checked={staff === 'Instructor'}
                            onChange={(e) => setStaff(e.target.value)}
                        />
                        Instructor
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="staff"
                            value="Student"
                            checked={staff === 'Student'}
                            onChange={(e) => setStaff(e.target.value)}
                        />
                        Student
                    </label>
                </div>
            </div>

            <div>
                <label htmlFor="bio">Bio:</label>
                <textarea
                    id="bio"
                    value={bio}
                    onChange={(e) => setBio(e.target.value)}
                ></textarea>
            </div>

            <div>
                <label>
                    <input
                        type="checkbox"
                        checked={emailNotifications}
                        onChange={() => setEmailNotifications(!emailNotifications)}
                    />
                    Sign up for email notifications
                </label>
            </div>

            <button type="submit">Sign Up</button>
        </form>
    );
};

export default Component;
