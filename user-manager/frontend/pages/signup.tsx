import React from 'react';
import { Grid, FormControl, InputLabel, Input, InputAdornment, IconButton, Button } from '@material-ui/core';
import { Visibility, VisibilityOff } from '@material-ui/icons';
import PhoneInput from 'react-phone-input-2';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    phoneNumberContainer: {
      margin: 0,
      width: '100%',
    },
    margin: {
      margin: theme.spacing(1),
    },
  }),
);

const phoneNumberInputStyle = {
  margin: 0,
  width: '100%',
  border: 'none',
  borderRadius: 0,
  background: 'none',
  paddingLeft: 0,
  paddingRight: '48px',
}

const buttonStyle = {
  border: 'none',
  borderRadius: 0,
  background: 'none',
  right: 0
}

const mainSingUpContainerStyle = {
  height: '100vh',
  padding: 0,
  margin: 0,
}

const formBlockStyle = {
  padding: 0,
  margin: 0,
}

interface State {
  phone: string;
  password: string;
  confirmPassword: string;
  showPassword: boolean;
  showConfirmPassword: boolean;
}

export default () => {
  const classes = useStyles();

  const [values, setValues] = React.useState<State>({
    phone: '',
    password: '',
    confirmPassword: '',
    showPassword: false,
    showConfirmPassword: false,
  });

  const handleChange = (prop: keyof State) => (event: React.ChangeEvent<HTMLInputElement>) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleClickShowConfirmPassword = () => {
    setValues({ ...values, showConfirmPassword: !values.showConfirmPassword });
  };

  const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
  };

  let isFocusPhoneNumberInput = false;

  return (
    <Grid container justify='center' style={mainSingUpContainerStyle} alignItems="center">
      <Grid lg={4} md={6} sm={8} xs={10}>
        <h1>Sign up</h1>
        <FormControl fullWidth margin='normal'>
          <InputLabel htmlFor="phone-number" shrink focused={isFocusPhoneNumberInput}>Phone number</InputLabel>
          <PhoneInput
            inputProps={{required: true, id: "phone-number", onFocus: () => isFocusPhoneNumberInput = true, onBlur: () => isFocusPhoneNumberInput = false}}
            masks={{fr: '(...) ..-..-..', at: '(....) ...-..-..'}}
            placeholder=""
            containerClass="MuiInputBase-root MuiInput-root MuiInput-underline MuiInputBase-formControl MuiInput-formControl MuiInputBase-adornedEnd"
            inputClass="MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd"
            inputStyle={phoneNumberInputStyle}
            buttonStyle={buttonStyle}
            />
        </FormControl>

        <FormControl fullWidth margin='normal'>
          <InputLabel htmlFor="password" shrink>Password</InputLabel>
          <Input
            id="password"
            type={values.showPassword ? 'text' : 'password'}
            value={values.password}
            onChange={handleChange('password')}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={handleClickShowPassword}
                  onMouseDown={handleMouseDownPassword}
                >
                  {values.showPassword ? <Visibility/> : <VisibilityOff/>}
                </IconButton>
              </InputAdornment>
            }
          />
        </FormControl>

        <FormControl fullWidth margin='normal'>
          <InputLabel htmlFor="confirm-password" shrink>Confirm password</InputLabel>
          <Input
            id="confirm-password"
            type={values.showConfirmPassword ? 'text' : 'password'}
            value={values.confirmPassword}
            onChange={handleChange('confirmPassword')}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle confirm password visibility"
                  onClick={handleClickShowConfirmPassword}
                  onMouseDown={handleMouseDownPassword}
                >
                  {values.showConfirmPassword ? <Visibility/> : <VisibilityOff/>}
                </IconButton>
              </InputAdornment>
            }
          />
        </FormControl>

        <Grid alignItems="center">
          <Button variant="contained" color="primary" className={classes.margin}>Create account</Button>
        </Grid>
      </Grid>
    </Grid>
  );
}

