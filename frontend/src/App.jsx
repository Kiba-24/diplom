import React, { useMemo, useState } from 'react'

const API = import.meta.env.VITE_API_URL || 'http://localhost:8080'

export default function App() {
  const [token, setToken] = useState('')
  const [tickets, setTickets] = useState([])
  const [form, setForm] = useState({ title: '', description: '', category: 'Общее' })
  const [chat, setChat] = useState('')
  const [chatResponse, setChatResponse] = useState('')

  const authHeaders = useMemo(() => ({ 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' }), [token])

  const login = async () => {
    const res = await fetch(`${API}/api/auth/login`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ username: 'operator', password: 'operator123' }) })
    const data = await res.json()
    setToken(data.token)
  }

  const loadTickets = async () => {
    const res = await fetch(`${API}/api/tickets`, { headers: authHeaders })
    setTickets(await res.json())
  }

  const createTicket = async () => {
    await fetch(`${API}/api/tickets`, { method: 'POST', headers: authHeaders, body: JSON.stringify(form) })
    setForm({ title: '', description: '', category: 'Общее' })
    loadTickets()
  }

  const askBot = async () => {
    const first = tickets[0]
    if (!first) return
    const res = await fetch(`${API}/api/chat`, { method: 'POST', headers: authHeaders, body: JSON.stringify({ ticketId: first.id, message: chat }) })
    const data = await res.json()
    setChatResponse(data.text)
  }

  return <div className="container">
    <h1>Служба поддержки + AI-чат-бот</h1>
    <button onClick={login}>Войти оператором</button>
    <button onClick={loadTickets} disabled={!token}>Загрузить обращения</button>
    <div className="panel">
      <h2>Создать обращение</h2>
      <input placeholder="Тема" value={form.title} onChange={e => setForm({ ...form, title: e.target.value })} />
      <textarea placeholder="Описание" value={form.description} onChange={e => setForm({ ...form, description: e.target.value })} />
      <input placeholder="Категория" value={form.category} onChange={e => setForm({ ...form, category: e.target.value })} />
      <button onClick={createTicket} disabled={!token}>Создать</button>
    </div>
    <div className="panel">
      <h2>Чат-бот</h2>
      <input placeholder="Сообщение для классификации" value={chat} onChange={e => setChat(e.target.value)} />
      <button onClick={askBot} disabled={!token || tickets.length===0}>Отправить в бота</button>
      <p>{chatResponse}</p>
    </div>
    <div className="panel"><h2>Обращения ({tickets.length})</h2>{tickets.slice(0,10).map(t => <div key={t.id}>#{t.id} {t.title} — {t.status}</div>)}</div>
  </div>
}
