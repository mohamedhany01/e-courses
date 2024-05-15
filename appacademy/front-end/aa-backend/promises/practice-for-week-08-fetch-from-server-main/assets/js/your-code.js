export function getAllDogs() {
    return fetch("/dogs");
}

export function getDogNumberTwo() {
    return fetch("/dogs/2");
}

export function postNewDog() {

    return fetch("/dogs", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            name: "Foo",
            age: 100
        })
    });
}

export function postNewDogV2(name, age) {
    return fetch("/dogs", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            name: name,
            age: age
        })
    });
}

export function deleteDog(id) {
    return fetch(`/dogs/${id}/delete`, {
        method: "POST",
        headers: {
            "AUTH": "ckyut5wau0000jyv5bsrud90y"
        }
    });
}
