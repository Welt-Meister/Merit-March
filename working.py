from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI()

userdata = {
    "yunus123": {
        "name": "Yunus Parvez",
        "pwd": "123"
    }
}

class SignupRequest(BaseModel):
    name: str
    username: str
    password: str

class SignupResponse(BaseModel):
    success: bool
    message: str

@app.post("/signup", response_model=SignupResponse)
def signup(request: SignupRequest):
    if request.username in userdata:
        raise HTTPException(status_code=400, detail="Username already exists")
    userdata[request.username] = {
        "name": request.name,
        "pwd": request.password
    }
    return SignupResponse(success=True, message="Signup successful")

@app.get("/login/{username}")
def get_user(username: str):
    if username in userdata:
        return userdata[username]
    else:
        raise HTTPException(status_code=404, detail="User Not Found")
