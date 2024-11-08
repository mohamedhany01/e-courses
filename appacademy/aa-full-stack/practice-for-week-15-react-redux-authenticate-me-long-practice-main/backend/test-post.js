fetch('/api/test', {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    // <value of XSRF-TOKEN cookie>
    "XSRF-TOKEN": `QDuCFlH9-SMSiLxpDrufQSPZbJNX4AdnNavY`
  },
  body: JSON.stringify({ hello: 'world' })
}).then(res => res.json()).then(data => console.log(data));


fetch('/api/session', {
  method: 'POST',
  headers: {
    "Content-Type": "application/json",
    "XSRF-TOKEN": `LAvbKzBc-J1bsNgCUtLg_RmRNkKTGFa9ICfM`
  },
  body: JSON.stringify({ credential: 'Demo-lition', password: 'password' })
}).then(res => res.json()).then(data => console.log(data));

fetch('/api/session', {
  method: 'DELETE',
  headers: {
    "Content-Type": "application/json",
    "XSRF-TOKEN": `97187qch-R8WjbM3beZHNwUX2PiQIntsvods`
  }
}).then(res => res.json()).then(data => console.log(data));


fetch('/api/users', {
  method: 'POST',
  headers: {
    "Content-Type": "application/json",
    "XSRF-TOKEN": `LAvbKzBc-J1bsNgCUtLg_RmRNkKTGFa9ICfM`
  },
  body: JSON.stringify({
    email: 'spidey@spider.man',
    username: 'Spidey',
    password: 'Spidey'
  })
}).then(res => res.json()).then(data => console.log(data));


fetch('/api/session', {
  method: 'GET',
  headers: {
    "Content-Type": "application/json",
  }
}).then(res => res.json()).then(data => console.log(data));


fetch('/api/users', {
  method: 'POST',
  headers: {
    "Content-Type": "application/json",
    "XSRF-TOKEN": `97187qch-R8WjbM3beZHNwUX2PiQIntsvods`
  },
  body: JSON.stringify({
    email: 'firestar@spider.man',
    username: 'Firestar',
    password: ''
  })
}).then(res => res.json()).then(data => console.log(data));

