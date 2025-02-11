import { RootState } from "../store";
import  {userNumber } from "@/types/user_number";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const initialState: { userNumber: userNumber | null } = {
  userNumber:null,
};


const userNumberSlice = createSlice({
  name: "room",
  initialState,
  reducers: {
    setUserNumber(state, action: PayloadAction<userNumber>) {
      state.userNumber = action.payload;
    },
  },
});



export const { setUserNumber } = userNumberSlice.actions;
export const selectUserNumber = (state: RootState) => state.userNumber.userNumber;
export default userNumberSlice.reducer;
