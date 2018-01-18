import axios from 'axios';

export function makeLoginCall(username, password) {
    return makePostOrPutApiRequest('POST', '/login', {username, password});
}

export function makeSignupCall(firstName, lastName, username, password) {
    return makePostOrPutApiRequest('POST', '/signup', {firstName, lastName, username, password});
}

export function makeLoginHistoryListCall() {
    return makeGetOrDeleteApiRequest('GET', '/login/history');
}

export function makeLoginHistoryClearCall() {
    return makeGetOrDeleteApiRequest('DELETE', '/login/history');
}

export function makePostOrPutApiRequest(method, path, body) {
    const baseURL = 'http://localhost:8080';
    return axios({
      method: method,
      url: baseURL + path,
      data: body
    });
}

export function makeGetOrDeleteApiRequest(method, path) {
    const baseURL = 'http://localhost:8080';
    return axios({
      method: method,
      url: baseURL + path,
      headers: {'Authorization': 'Bearer' + localStorage.getItem('token')}
    });
}
