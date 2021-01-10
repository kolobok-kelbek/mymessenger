import { Phone } from '@material-ui/icons';

export default () => {
  return (
    <div className="login-box">
      <form action="#">
        <h1>Login</h1>
        <div className="textbox">
          <Phone style={{float: "left"}}/>
          {/*<i className="fas fa-phone"></i>*/}
          <input type="text" pattern="^[0-9-+\s()]*$" maxLength={11} placeholder="Enter Number" required={true}/>
        </div>

        <div className="textbox">
          {/*<i className="fas fa-key"></i>*/}
          <input type="password" placeholder="Enter Password"/>
        </div>
        <input className="btn" type="submit" value="Sign in"/>
      </form>
    </div>
  );
}
