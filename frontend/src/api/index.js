import axios from 'axios'
import { useTokenStore } from "@/stores/token"

const store = useTokenStore();

function apiInstance() {
    
    const instance = axios.create({
        baseURL: 'https://i9a403.p.ssafy.io/api',
        //baseURL: 'http://localhost:5000/api',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization': store.getAccessToken
        }
    });
    return instance;
}

export { apiInstance }