# Auth0 Setup

## Applications

**Name**: MineCount  
**Application Type**: Regular Web Application

## API

**Name**: MineCount  
**RBAC**: Enabled  
**Add Permissions in the Access Token**: Enabled  
**Allow Offline Access**: Enabled
**Permissions**:

- approve:servers
- update:servers

## Actions

### Add email address to access token

**Flow**: Login

```js
exports.onExecutePostLogin = async (event, api) => {
  if (event.authorization) {
    api.accessToken.setCustomClaim("/minecount/user_id", event.user.user_id);
    api.accessToken.setCustomClaim("/minecount/email", event.user.email);
  }
};
```
