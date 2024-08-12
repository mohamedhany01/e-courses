# `useEffect` Hook Rendering

One of the most important things to understand about the `useEffect` hook is how
it renders. Every `useEffect` hook renders after the first render of a
functional component. Whether the `useEffect` function ever runs again after
that depends on different factors.

When you finish this article you should:

- Understand the two arguments in a `useEffect` function
- Know when a `useEffect` function renders
- Understand how to use the optional dependency array
- Know how to use the optional cleanup function
- Understand when to use the optional cleanup function

## Setup for following along

If you want to follow along, click the `Download Project` button at the bottom
of this page to go to the starter repo, then load the repo into [CodeSandbox].

Alternatively, you could also create a `create-react-app` application as you did
in the previous practice.

## Create a Main Component

In your `src` folder, create a file called __Main.js__. Create a functional
component called `Main` with an `h1` containing the text "Main Component". Make
`Main` the default export.

```js
// src/Main.js
const Main = () => {
  return (
    <div>
      <h1>Main Component</h1>
    </div>
  );
};

export default Main;
```

Add the `Main` component to your __App.js__ by importing it and replacing
`App`'s `h1` tag with the `Main` component.

```js
// src/App.js
import Main from './Main';

function App() {
  return (
    <>
      <Main />
    </>
  );
}

export default App;
```

## `useEffect` with no dependency array

With no dependency array, `useEffect` runs after the first render and every
subsequent re-render.

Add the code below to your `Main` component file:

```js
import { useEffect } from 'react';

const Main = () => {
  useEffect(() => {
    console.log('UseEffect1 Ran');
  });

  return (
    <div>
      {console.log('rendered or re-rendered')}
      <h1>Main Component</h1>
    </div>
  );
};

export default Main;
```

Notice that the `useEffect` has been called with just one argument, the callback
function.

Refresh your page in the browser and look in the DevTools console. (Don't forget
to run `npm start` to start your project if you are not working in CodeSandbox.)
You should see:

```text
rendered or re-rendered
UseEffect1 Ran
```

Now you have seen that the `useEffect` runs after the first render. But what
about when the `Main` component re-renders?

Import `useState` and add a slice of state named `ToggleOne` to your `Main`
component with an initial state boolean of `false`.

Your state should look similar to the code below:

```js
const [toggleOne, setToggleOne] = useState(false);
```

Next, below your `h1` create a Button with the child text "ToggleOne". Add an
`onClick` event handler to the button. Using the `setToggleOne` updater
function, change the value of the `toggleOne` state to the opposite value.
(I.e., if the value is true then it should change to false and vice-versa).

```js
<button onClick={() => setToggleOne(!toggleOne)}>ToggleOne</button>
```

Now test again in the browser console.

Each time you click the `ToggleOne` button you should see an additional

```text
rendered or re-rendered
UseEffect1 Ran
```

## `useEffect` with an empty dependency array

`useEffect` with an empty dependency array will run only one time, directly
after the first render.

Add an empty dependency array as a second argument to your `useEffect`:

```js
useEffect(() => {
  console.log('UseEffect1 Ran');
}, []);
```

Refresh your browser and test again. On the first render you will again see:

```text
rendered or re-rendered
UseEffect1 Ran
```

Now click the `ToggleOne` button repeatedly. Every click updates the state,
which triggers a subsequent re-render. However, because you added an empty
dependency array as the second argument to the `useEffect`, the `useEffect`
never runs again. You will instead see only:

```text
rendered or re-rendered
```

followed by a number incrementing next to that log for each click.

## Adding state to the `useEffect` dependency array

The `useEffect` dependency array can be used to listen for changes in either
state or props.

In your `Main` component, add a new slice of state that returns a destructured
`toggleTwo` and `setToggleTwo` variable. The initial value should be set to
false. It should look identical to the `toggleOne` slice of state except for the
naming:

```js
const [toggleTwo, setToggleTwo] = useState(false);
```

In your JSX below the `ToggleOne` button, add a `ToggleTwo` button. It should
also be identical to the previous button except for the name.

Add another `useEffect` beneath the first one that looks like this:

```js
useEffect(() => {
  console.log('UseEffect2 Ran');
}, [toggleTwo]);
```

Here is the new order. When the page renders, you will see

```text
rendered or re-rendered
UseEffect1 Ran
UseEffect2 Ran
```

Now, when you click on the `ToggleTwo` button it will trigger an order of
events:

1. You click on the `ToggleTwo` button.
2. The `toggleTwo` state will be updated.
3. The `Main` component will re-render.
4. The `useEffect` function that has `toggleTwo` in the dependency array will
   run again.

Notice if you click the `toggleOne` button the only thing logged is

```text
rendered or re-rendered
```

## Running functionality in `useEffect` based on a condition

Sometimes you may only want the functionality of your `useEffect` to run when a
certain condition is met. You cannot stop a `useEffect` from running if a
variable in its dependency array changes, but you can block certain code from
executing by using an `if` conditional to check the state or prop for a specific
value.

In your second `useEffect`, beneath the `console.log`, create an `if`
conditional that checks to see if `toggleTwo` is true. If it is true, log
`toggleTwo slice of state is true so this code runs`. Your `useEffect` should
look like this now:

```js
useEffect(() => {
  console.log('UseEffect2 Ran');
  if (toggleTwo)
    console.log('toggleTwo slice of state is true so this code runs');
}, [toggleTwo]);
```

You will only see this second console log in the console when the `toggleTwo`
slice of state is true. Test in your browser with the console!

## `useEffect`'s optional cleanup function

The `useEffect` hook has an optional cleanup function that can be used to
cleanup some behavior (e.g., a running function) when a component unmounts or,
as you will see below, when that behavior needs to be stopped.

Note that the `useEffect` cleanup function will not run after the first render,
but it will run after the second render, just **BEFORE** the `useEffect`
function runs.

Order after render:

1. Component re-renders
2. `useEffect` cleanup function runs, clearing out any specified previous
   value(s)
3. `useEffect` function body runs

Create a `count` slice of state with an initial value of `0`.

```js
const [count, setCount] = useState(0);
```

Beneath the `setToggleTwo` button, create a button for "Count" and use the
`onClick` event listener to increment the count.

Create a third `useEffect` that listens for the `count` slice of state.

In the body of the callback function, add a `setInterval` function that looks
like the one below:

```js
useEffect(() => {
  setInterval(() => {
    console.log(`UseEffect3 with interval number ${count} is running`);
  }, 1000);
}, [count]);
```

Open your browser console again. Click on the increment button and notice what
happens. You will see that each time the count button is clicked, the
`setInterval` function is called in the `useEffect` with a new value. However,
the old `setInterval` function has not been cleared. This causes a memory leak.
You should see all of the previous `setInterval`s running along with the new one
that has been created.

To fix this problem, use the cleanup function. First, assign the `setInterval`
function to a variable called `myInterval`.

Then below the `myInterval` variable, insert the following cleanup function:

```js
return () => {
  console.log(
    `UseEffect3 cleanup ran.\nsetInterval number ${count} is being cleared out`
  );
  clearInterval(myInterval);
};
```

Test in your browser. Now you should see only one `setInterval` running at any
given time.

[CodeSandbox]: https://www.codesandbox.io