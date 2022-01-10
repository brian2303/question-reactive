import React from 'react'
import { Link } from 'react-router-dom'
import ContactSupportOutlinedIcon from '@mui/icons-material/ContactSupportOutlined';

export const PublicNavbar = () => (
  <nav>
    <div className='logo'>
    <ContactSupportOutlinedIcon />
    </div>
    <section>
      <Link to="/">Home</Link>
      <Link to="/questions">Questions</Link>
    </section>
  </nav>
)

export const PrivateNavbar = () => (
  <nav>
    <div className='logo'>
      <ContactSupportOutlinedIcon />
    </div>
    <section>
      <Link to="/">Home</Link>
      <Link to="/questions">Questions</Link>
      <Link to="/new">New</Link>
      <Link to="/list">My questions</Link>
      <Link to="/favorites">My favorites</Link>
    </section>
  </nav>
)
